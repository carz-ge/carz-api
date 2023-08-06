package ge.carapp.carappapi.controller.ws.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ge.carapp.carappapi.controller.ws.WsUtils;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static ge.carapp.carappapi.controller.ws.WsUtils.clearClosedConnections;
import static ge.carapp.carappapi.security.AuthenticatedUserProvider.authenticatedUserDetails;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserOrderWebSocketHandler extends TextWebSocketHandler {
    //    private final ConcurrentWebSocketSessionDecorator
    private final ObjectMapper objectMapper;

    private static final int MAX_CONNECTION_PER_USER = 2;


    private final ConcurrentHashMap<UUID, List<WebSocketSession>> userConnections =
            new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        CustomUserDetails customUserDetails = authenticatedUserDetails(((PreAuthenticatedAuthenticationToken) Objects.requireNonNull(session.getPrincipal())));
        UserEntity user = customUserDetails.user();
        List<WebSocketSession> webSocketSessions = userConnections.computeIfAbsent(user.getId(), key -> Collections.synchronizedList(new LinkedList<>()));

        clearClosedConnections(webSocketSessions, MAX_CONNECTION_PER_USER);


        webSocketSessions.add(session);

        log.info("userConnections size {}", userConnections.size());

        // Handle new WebSocket connection
        log.info("afterConnectionEstablished USER session: {}, {}, {}, {}, {}",
                session.getId(),
                session.getAttributes(),
                session.getPrincipal(),
                session.getAcceptedProtocol(),
                session.getHandshakeHeaders());
    }



    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws IOException {
        // Handle incoming WebSocket message
        log.info("USER handleTextMessage: {}", message);

        CustomUserDetails customUserDetails = authenticatedUserDetails(((PreAuthenticatedAuthenticationToken) Objects.requireNonNull(session.getPrincipal())));

        session.sendMessage(new TextMessage("user message received: %s".formatted(message)));
    }

    public boolean sendMessage(UserEntity user, UserOrderWsMessage message) {
        try {
            return sendMessage(user, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            log.error("Json error while sending message to user",e);
            return false;
        }
    }



    private boolean sendMessage(UserEntity user, String message) {
        return WsUtils.sendMessage(userConnections,user.getId(), message);
    }


    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        // Handle closed WebSocket connection
        log.info("afterConnectionClosed: {}", status);
    }
}
