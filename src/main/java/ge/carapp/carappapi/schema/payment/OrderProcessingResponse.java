package ge.carapp.carappapi.schema.payment;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderProcessingResponse(
    @NotNull UUID bogOrderId,
    String redirectLink
) {
}
