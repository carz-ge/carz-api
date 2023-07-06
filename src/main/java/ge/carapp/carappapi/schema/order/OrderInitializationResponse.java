package ge.carapp.carappapi.schema.order;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderInitializationResponse(
    UUID id,
    String idempotencyKey,
    String redirectLink,
    Integer totalPrice,
    @NotNull UUID productId,
    @NotNull UUID packageId,
    UUID providerId,
    UUID categoryId,
    String schedulingDay,
    String schedulingTime,
    OrderStatus status
) {
}
