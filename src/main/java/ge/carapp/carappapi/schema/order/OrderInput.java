package ge.carapp.carappapi.schema.order;

import ge.carapp.carappapi.schema.CarType;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record OrderInput(
    @NotNull String idempotencyKey,
    @NotNull UUID productId,
    @NotNull UUID packageId,
    String schedulingDay,
    String schedulingTime,
    CarType carType,
    String carPlateNumber,
    String comment
) {
}
