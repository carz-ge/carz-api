package ge.carapp.carappapi.models.bog.order;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PurchaseInfo(
    @NotNull String totalAmount,
    @NotEmpty List<ProductBucket> bucket,

    String totalDiscountAmount,
    Delivery delivery,
    String currency // GEL, USD, EUR, GBP
) {
}
