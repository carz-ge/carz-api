package ge.carapp.carappapi.controller.ws.manager;

import ge.carapp.carappapi.entity.ManagerEntity;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.security.CustomUserDetails;
import ge.carapp.carappapi.service.ManagerService;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static ge.carapp.carappapi.security.AuthenticatedUserProvider.authenticatedUserDetails;

@Slf4j
@Component
@RequiredArgsConstructor
public class ManagersOrderWebSocketHandler extends TextWebSocketHandler {
    private final ConcurrentHashMap<UUID, ConcurrentHashMap<UUID, WebSocketSession>> providerManagerConnections =
        new ConcurrentHashMap<>();

    private final ManagerService managerService;

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) {
        ManagerEntity manager = getManagerFromSession(session);


        ConcurrentHashMap<UUID, WebSocketSession> managerToConnection =
            providerManagerConnections.computeIfAbsent(manager.getProviderId(), a -> new ConcurrentHashMap<>());

        managerToConnection.put(manager.getId(), session);

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

    public boolean sendMessage(ManagerEntity manager, String message) {
        ConcurrentHashMap<UUID, WebSocketSession> managerToConnections = providerManagerConnections.get(manager.getProviderId());
        if (managerToConnections == null) {
            return false;
        }
        WebSocketSession webSocketSession = managerToConnections.get(manager.getId());
        if (webSocketSession == null || !webSocketSession.isOpen()) {
            return false;
        }

        try {
            webSocketSession.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.error("could not send message", e);
            return false;
        }
        return true;
    }

    public List<UUID> sendMessageToAll(UUID providerId, String message) {
        ConcurrentHashMap<UUID, WebSocketSession> managerToConnections = providerManagerConnections.get(providerId);
        if (managerToConnections == null) {
            return Collections.emptyList();
        }

        List<UUID> success = new ArrayList<>();

        managerToConnections.forEach((id, conn)-> {
            WebSocketSession webSocketSession = managerToConnections.get(id);
            if (webSocketSession == null || !webSocketSession.isOpen()) {
                return;
            }

            try {
                webSocketSession.sendMessage(new TextMessage(message));
                success.add(id);
            } catch (IOException e) {
                log.error("could not send message", e);
            }

        });

        return success;
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, @NotNull TextMessage message) throws IOException {
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
