package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.ChatMessageSchema;
import ge.carapp.carappapi.security.AuthenticatedUserProvider;
import ge.carapp.carappapi.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<ChatMessageSchema> listChatMessages() {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return chatService.listMessages(authenticatedUser);
    }
}
