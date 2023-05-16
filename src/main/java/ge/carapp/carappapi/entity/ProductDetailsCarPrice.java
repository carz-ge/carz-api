package ge.carapp.carappapi.entity;

import ge.carapp.carappapi.schema.CarType;

public record ProductDetailsCarPrice(
    int order,
    CarType carType,
    int price
) {
}
