package ge.carapp.carappapi.schema.graphql;


import ge.carapp.carappapi.schema.CarType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CarInput(
    @NotEmpty String plateNumber,
    @NotNull CarType carType,
    String techPassportNumber,
    String vin,
    String make,
    String model,
    int year
) {
}
