package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.ChatMessageEntity;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatMessageSchema(
    String id,
    LocalDateTime createdAt,
    String text,
    Boolean isAnswer,
    ChatMessageStatus statusChatMessageStatus

) {
    public static ChatMessageSchema convert(ChatMessageEntity chatMessageEntity) {
        return ChatMessageSchema.builder()
            .id(chatMessageEntity.getId())
            .text(chatMessageEntity.getText())
            .createdAt(chatMessageEntity.getCreatedAt())
            .statusChatMessageStatus(chatMessageEntity.getStatusChatMessageStatus())
            .isAnswer(chatMessageEntity.getIsAnswer())
            .build();
    }
}
