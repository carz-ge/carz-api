package ge.carapp.carappapi.schema;

import java.util.UUID;

public record ProductDetailsCarPriceSchema(UUID id, CarType carType, int price) {
}
