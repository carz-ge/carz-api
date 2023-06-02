package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.ChatMessageSchema;
import ge.carapp.carappapi.security.AuthenticatedUserProvider;
import ge.carapp.carappapi.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class SageController {

    private final ChatService chatService;

    @GetMapping("/sage")
    @ResponseBody
//    @PreAuthorize("isAuthenticated()")
    public Flux<ServerSentEvent<String>> sage(@RequestParam("q") String question) {
//        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return chatService.askQuestion(null, question);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<ChatMessageSchema> listChatMessages() {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return chatService.listMessages(authenticatedUser);
    }
}
