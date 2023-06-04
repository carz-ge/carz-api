package ge.carapp.carappapi.controller.ws;

import ge.carapp.carappapi.security.CustomUserDetails;
import ge.carapp.carappapi.service.chat.ChatService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Objects;

import static ge.carapp.carappapi.security.AuthenticatedUserProvider.authenticatedUserDetails;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {
    private final ChatService chatService;

    public Flux<String> askSage(String question) {
        // A flux is the publisher of data
        return Flux.interval(Duration.ofSeconds(1))
            .map(i -> "Sage answer " + i + " \n"
            )
            .take(2)
            .log();

    }

    @Override
    public void afterConnectionEstablished(@NotNull WebSocketSession session) throws Exception {
        // Handle new WebSocket connection
        log.info("afterConnectionEstablished session: {}, {}, {}, {}, {}",
            session.getId(),
            session.getAttributes(),
            session.getPrincipal(),
            session.getAcceptedProtocol(),
            session.getHandshakeHeaders());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, @NotNull TextMessage message) {
        // Handle incoming WebSocket message
        log.info("handleTextMessage: {}", message);

        CustomUserDetails customUserDetails = authenticatedUserDetails(((PreAuthenticatedAuthenticationToken) Objects.requireNonNull(session.getPrincipal())));

        chatService.askQuestion(customUserDetails.user(), message.getPayload())
            .subscribe(chunk -> {
                log.info("chunk: {}", chunk);
                try {
                    if (session.isOpen()) {
                        session.sendMessage(new TextMessage(chunk));
                    } else {
                        log.info("session is not open");
                    }

                } catch (Exception e) {
                    log.error("Error sending message", e);
                }
            });
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        // Handle closed WebSocket connection
        log.info("afterConnectionClosed: {}", status);
    }
}
