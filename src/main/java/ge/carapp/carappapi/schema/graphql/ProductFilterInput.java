package ge.carapp.carappapi.schema.graphql;

import ge.carapp.carappapi.schema.CarType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ProductFilterInput(
    UUID categoryId,
    CarType carType,
    LocalDate date,
    LocalTime time
) {
}
