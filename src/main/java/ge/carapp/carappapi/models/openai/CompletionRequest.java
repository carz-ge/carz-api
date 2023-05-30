package ge.carapp.carappapi.models.openai;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record CompletionRequest(
    @NotNull String model,
    @NotNull List<ChatMessage> messages,

    Double temperature,
    Double topP,
    Integer n,
    Boolean stream,
    List<String> stop,

    Integer maxTokens,


    Double presencePenalty,
    Double frequencyPenalty

//     Map<> logit_bias,
//    String user


) {
}
