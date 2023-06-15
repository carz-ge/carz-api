package ge.carapp.carappapi.models.bog.order;

import jakarta.validation.constraints.NotNull;

public record ProductBucket(
    @NotNull String productId,
    @NotNull String unitPrice,
    int quantity,
    String description,
    String unitDiscountPrice,
    String image
) {
}
