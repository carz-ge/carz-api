package ge.carapp.carappapi.schema.order;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderedProduct(
    @NotNull UUID productId,
    @NotNull UUID packageId,
    String schedulingDate,
    String schedulingTime
) {
}
