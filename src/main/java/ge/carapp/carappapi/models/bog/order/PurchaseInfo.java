package ge.carapp.carappapi.models.bog.order;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record PurchaseInfo(
    @JsonProperty("total_amount")
    double totalAmount,
    @NotEmpty List<ProductBasket> basket,

    @JsonProperty("total_discount_amount")
    String totalDiscountAmount,
    Delivery delivery,
    String currency // GEL, USD, EUR, GBP
) {
}
