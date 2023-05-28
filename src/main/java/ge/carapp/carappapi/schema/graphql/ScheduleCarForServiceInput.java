package ge.carapp.carappapi.schema.graphql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ScheduleCarForServiceInput(

    UUID productId,
    String carPlateNumber,
    String customerPhoneNumber,

    LocalDate schedulingDay,
    LocalTime schedulingTime

) {
}
