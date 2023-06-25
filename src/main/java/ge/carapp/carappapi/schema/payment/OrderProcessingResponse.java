package ge.carapp.carappapi.schema.payment;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderProcessingResponse(
    String idempotencyKey,
    UUID orderId,
    String redirectLink
) {
}
