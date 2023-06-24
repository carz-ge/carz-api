package ge.carapp.carappapi.schema.payment;

import lombok.Builder;

@Builder
public record OrderProcessingResponse(
    String idempotencyKey,
    String orderId,
    String redirectLink
    ) {
}
