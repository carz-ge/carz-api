package ge.carapp.carappapi.models.bog.order;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record PurchaseInfo(
    double totalAmount,
    @NotEmpty List<ProductBasket> basket,

    String totalDiscountAmount,
    Delivery delivery,
    String currency // GEL, USD, EUR, GBP
) {
}
