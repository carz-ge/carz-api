package ge.carapp.carappapi.models.openai;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EventChoice(
    Delta delta,

    Integer index,
    @JsonProperty("finish_reason")
    String finishReason
) {
}


