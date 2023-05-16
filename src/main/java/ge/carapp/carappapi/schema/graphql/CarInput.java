package ge.carapp.carappapi.schema.graphql;


import ge.carapp.carappapi.schema.CarType;

public record CarInput(
    String plateNumber,
    CarType carType,
    String techPassportNumber,
    String vin,
    String make,
    String model,
    int year
) {
}
