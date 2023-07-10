package ge.carapp.carappapi.schema.graphql;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ScheduleCarForServiceInput(

    @NotNull UUID productId,
    String carPlateNumber,
    String customerPhoneNumber,

    LocalDate schedulingDate,
    LocalTime schedulingTime

) {
}
