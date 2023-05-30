package ge.carapp.carappapi.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class EchoController {

    @QueryMapping
    public String echo(@Argument String message) {
        return message;
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String echoAuthorized(@Argument String message) {
        return message;
    }

    @QueryMapping
    Mono<String> echoMono(@Argument String message) {
        return Mono.just(message);
    }

    @GetMapping("/echo")
    ResponseEntity<String> echoRest(@PathParam("message") String message) {
        return ResponseEntity.ok(message);
    }

    @QueryMapping
    public Flux<List<String>> echoFlux(@Argument String message) {

        // A flux is the publisher of data
        return Flux.fromStream(
            Stream.generate(() -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    return List.of(message, Instant.now().toString());
                })
                .limit(5));

    }
}
