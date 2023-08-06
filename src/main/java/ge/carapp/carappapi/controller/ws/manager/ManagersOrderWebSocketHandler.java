package ge.carapp.carappapi.controller.ws.manager;

import ge.carapp.carappapi.controller.ws.WsUtils;
import ge.carapp.carappapi.entity.ManagerEntity;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.security.CustomUserDetails;
import ge.carapp.carappapi.service.ManagerService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static ge.carapp.carappapi.controller.ws.WsUtils.clearClosedConnections;
import static ge.carapp.carappapi.security.AuthenticatedUserProvider.authenticatedUserDetails;

@Slf4j
@Component
@RequiredArgsConstructor
public class ManagersOrderWebSocketHandler extends TextWebSocketHandler {
    private static final int MAX_CONNECTION_PER_MANAGER = 2;

    private final ConcurrentHashMap<UUID, Map<UUID, List<WebSocketSession>>> providerManagerConnections =
            new ConcurrentHashMap<>();

    private final ManagerService managerService;

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws IOException {
        ManagerEntity manager = getManagerFromSession(session);


        Map<UUID, List<WebSocketSession>> managerToConnection =
                providerManagerConnections.computeIfAbsent(manager.getProviderId(), a -> new ConcurrentHashMap<>());
        List<WebSocketSession> webSocketSessions = managerToConnection.computeIfAbsent(manager.getId(), key -> Collections.synchronizedList(new LinkedList<>()));

        clearClosedConnections(webSocketSessions, MAX_CONNECTION_PER_MANAGER);

        webSocketSessions.add(session);

        log.info("managerToConnection size {}", managerToConnection.size());

        // Handle new WebSocket connection
        log.info("afterConnectionEstablished MANAGER session: {}, {}, {}, {}, {}",
                session.getId(),
                session.getAttributes(),
                session.getPrincipal(),
                session.getAcceptedProtocol(),
                session.getHandshakeHeaders());
    }

    private ManagerEntity getManagerFromSession(@NotNull WebSocketSession session) {
        CustomUserDetails customUserDetails = authenticatedUserDetails(((PreAuthenticatedAuthenticationToken) Objects.requireNonNull(session.getPrincipal())));
        UserEntity user = customUserDetails.user();
        return managerService.getManager(user);
    }

    public boolean sendMessage(@NotNull ManagerEntity manager, String message) {
        Map<UUID, List<WebSocketSession>> managerToConnections = providerManagerConnections.get(manager.getProviderId());
        if (managerToConnections == null) {
            return false;
        }
        return WsUtils.sendMessage(managerToConnections, manager.getId(), message);
    }

    public List<UUID> sendMessageToAll(UUID providerId, String message) {
        Map<UUID, List<WebSocketSession>> managerToConnections = providerManagerConnections.get(providerId);
        if (managerToConnections == null) {
            return Collections.emptyList();
        }

        List<UUID> success = new ArrayList<>();

        managerToConnections.forEach((id, conn) -> {
            log.info("sending message to manager {}", id);
            if (WsUtils.sendMessage(managerToConnections, id, message)) {
                success.add(id);
            }
        });

        return success;
    }


    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws IOException {
        // Handle incoming WebSocket message
        log.info("MANAGER handleTextMessage: {}", message);
        ManagerEntity manager = getManagerFromSession(session);

        CustomUserDetails customUserDetails = authenticatedUserDetails(((PreAuthenticatedAuthenticationToken) Objects.requireNonNull(session.getPrincipal())));

        session.sendMessage(new TextMessage("message received: %s".formatted(message.getPayload())));
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        // Handle closed WebSocket connection
        log.info("afterConnectionClosed: {}", status);
    }
}
