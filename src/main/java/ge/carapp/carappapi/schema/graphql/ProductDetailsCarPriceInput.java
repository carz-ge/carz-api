package ge.carapp.carappapi.schema.graphql;

import ge.carapp.carappapi.schema.CarType;

public record ProductDetailsCarPriceInput(
    CarType carType,
    int price) {
}
