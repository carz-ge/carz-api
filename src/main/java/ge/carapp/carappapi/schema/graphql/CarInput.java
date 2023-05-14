package ge.carapp.carappapi.schema.graphql;

import lombok.Data;

@Data
public class CarInput {
    String plateNumber;
    CarType carType;
    String techPassportNumber;
    String vin;
    String make;
    String model;
    int year;
}
