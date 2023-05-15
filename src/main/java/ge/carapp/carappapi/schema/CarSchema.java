package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.CarEntity;
import ge.carapp.carappapi.schema.graphql.CarType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record CarSchema(
    UUID id,
    String plateNumber,
    CarType carType,
    String techPassportNumber,
    String vin,
    String make,
    String model,
    int year,

    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static CarSchema convert(CarEntity carEntity) {
        return CarSchema.builder()
            .id(carEntity.getId())
            .plateNumber(carEntity.getPlateNumber())
            .carType(carEntity.getCarType())
            .techPassportNumber(carEntity.getTechPassportNumber())
            .vin(carEntity.getVin())
            .make(carEntity.getMake())
            .model(carEntity.getModel())
            .year(carEntity.getYear())
            .createdAt(carEntity.getCreatedAt())
            .updatedAt(carEntity.getUpdatedAt())
            .build();
    }
}
