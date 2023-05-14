package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.CarEntity;
import ge.carapp.carappapi.schema.graphql.CarType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CarSchema {
    private UUID id;
    private String plateNumber;
    private CarType carType;
    private String techPassportNumber;
    private String vin;
    private String make;
    private String model;
    private int year;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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
