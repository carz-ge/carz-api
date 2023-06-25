package ge.carapp.carappapi.service.notification.discord;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;


@Service()
@Slf4j
public class DiscordService {
    private final WebClient client;

    public DiscordService(@Value("${discord.api}") String api) {
        this.client = WebClient.create(api);
    }

    public Mono<Boolean> sendMessage(String message) {
        log.info("sending Discord notification: {}", message);
        return this.client
            .post()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(Map.of("content", message))
            .retrieve()
            .toBodilessEntity()
            .map(r -> HttpStatus.ACCEPTED.equals(r.getStatusCode()))
            .onErrorResume(e -> {
                log.error("discord error occurred", e);
                return Mono.just(false);
            });

    }
}
