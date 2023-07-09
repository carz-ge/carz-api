package ge.carapp.carappapi.schema.order;

import ge.carapp.carappapi.entity.OrderEntity;
import ge.carapp.carappapi.schema.CarType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record OrderSchema(
    UUID id,
    Integer totalPrice,
    Integer commission,

    OrderStatus status,

    UUID productId,
    UUID packageId,
    UUID categoryId,
    UUID providerId,

    String schedulingDay,
    String schedulingTime,
    CarType carType,
    String carPlateNumber,
    String errorMessage,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static OrderSchema convert(OrderEntity orderEntity) {
        return OrderSchema.builder()
            .id(orderEntity.getId())
            .totalPrice(orderEntity.getTotalPrice())
            .commission(orderEntity.getCommission())
            .status(orderEntity.getStatus())
            .productId(orderEntity.getProductId())
            .packageId(orderEntity.getPackageId())
            .providerId(orderEntity.getProviderId())
            .categoryId(orderEntity.getCategoryId())
            .schedulingDay(orderEntity.getSchedulingDay())
            .schedulingTime(orderEntity.getSchedulingTime())
            .carType(orderEntity.getCarType())
            .carPlateNumber(orderEntity.getCarPlateNumber())
            .errorMessage(orderEntity.getErrorMessage())
            .createdAt(orderEntity.getCreatedAt())
            .updatedAt(orderEntity.getUpdatedAt())
            .build();
    }
}
