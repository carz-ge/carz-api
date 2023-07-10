package ge.carapp.carappapi.controller.ws.user;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.security.CustomUserDetails;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static ge.carapp.carappapi.security.AuthenticatedUserProvider.authenticatedUserDetails;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserOrderWebSocketHandler extends TextWebSocketHandler {
//    private final ConcurrentWebSocketSessionDecorator
    private final ConcurrentHashMap<UUID, WebSocketSession> userConnections  =
    new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        CustomUserDetails customUserDetails = authenticatedUserDetails(((PreAuthenticatedAuthenticationToken) Objects.requireNonNull(session.getPrincipal())));
        UserEntity user = customUserDetails.user();
        userConnections.put(user.getId(), session);

        // Handle new WebSocket connection
        log.info("afterConnectionEstablished USER session: {}, {}, {}, {}, {}",
            session.getId(),
            session.getAttributes(),
            session.getPrincipal(),
            session.getAcceptedProtocol(),
            session.getHandshakeHeaders());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, @NotNull TextMessage message) throws IOException {
        // Handle incoming WebSocket message
        log.info("USER handleTextMessage: {}", message);

        CustomUserDetails customUserDetails = authenticatedUserDetails(((PreAuthenticatedAuthenticationToken) Objects.requireNonNull(session.getPrincipal())));

        session.sendMessage(new TextMessage("user message received: %s".formatted(message)));
    }

    public boolean sendMessage(UserEntity user, String message) {
        WebSocketSession webSocketSession = userConnections.get(user.getId());
        if (webSocketSession == null || !webSocketSession.isOpen()) {
            return false;
        }

        try {
            webSocketSession.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        // Handle closed WebSocket connection
        log.info("afterConnectionClosed: {}", status);
    }
}
