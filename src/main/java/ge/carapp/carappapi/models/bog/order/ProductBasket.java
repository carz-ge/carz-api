package ge.carapp.carappapi.models.bog.order;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ProductBasket(
    @NotNull String productId,
    double unitPrice,
    int quantity,
    String description,
    String unitDiscountPrice,
    String image
) {
}
