package ge.carapp.carappapi.models.openai;

import ge.carapp.carappapi.entity.ChatMessageEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ChatMessage(
    @NotNull String role, // one of AuthorRole
    @NotNull String content
) {

    public static ChatMessage convert(ChatMessageEntity  entity) {
        return ChatMessage.builder()
                .role(entity.getIsAnswer() ? AuthorRole.ASSISTANT.toString() : AuthorRole.USER.toString())
                .content(entity.getText())
                .build();
    }
}
