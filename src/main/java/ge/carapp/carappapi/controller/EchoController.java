package ge.carapp.carappapi.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

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

}
