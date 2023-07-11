package ge.carapp.carappapi.models.bog.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record AutomaticOrderRequest(
    @JsonProperty("callback_url")
    @NotNull String callbackUrl,
    @JsonProperty("external_order_id")
    String externalOrderId
) {
}
