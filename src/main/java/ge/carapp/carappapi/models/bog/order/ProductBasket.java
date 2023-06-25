package ge.carapp.carappapi.models.bog.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ProductBasket(
    @JsonProperty("product_id")
    @NotNull String productId,
    @JsonProperty("unit_price")
    double unitPrice,
    int quantity,
    String description,
    @JsonProperty("unit_discount_price")
    String unitDiscountPrice,
    String image
) {
}
