package ge.carapp.carappapi.models.openai;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ChatMessage(
    @NotNull String role,
    @NotNull String content,
    String name
) {
}
