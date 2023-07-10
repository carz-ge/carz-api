package ge.carapp.carappapi.schema.order;

import ge.carapp.carappapi.entity.OrderEntity;
import ge.carapp.carappapi.schema.CarType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Builder
public record OrderInitializationResponse(
    UUID id,
    String idempotencyKey,
    String redirectLink,
    Integer totalPrice,
    Integer commission,
    @NotNull UUID productId,
    @NotNull UUID packageId,
    UUID providerId,
    UUID categoryId,
    LocalDate schedulingDate,
    LocalTime schedulingTime,
    OrderStatus status,
    CarType carType,
    String carPlateNumber,
    String errorMessage,
    LocalDateTime createdAt
) {

    public static OrderInitializationResponse convert(OrderEntity orderEntity) {
        return OrderInitializationResponse.builder()
            .id(orderEntity.getId())
            .totalPrice(orderEntity.getTotalPrice())
            .commission(orderEntity.getCommission())
            .idempotencyKey(orderEntity.getIdempotencyKey())
            .redirectLink(orderEntity.getRedirectLink())
            .productId(orderEntity.getProductId())
            .packageId(orderEntity.getPackageId())
            .categoryId(orderEntity.getCategoryId())
            .providerId(orderEntity.getProviderId())
            .schedulingDate(orderEntity.getSchedulingDate())
            .schedulingTime(orderEntity.getSchedulingTime())
            .status(orderEntity.getStatus())
            .carType(orderEntity.getCarType())
            .carPlateNumber(orderEntity.getCarPlateNumber())
            .errorMessage(orderEntity.getErrorMessage())
            .createdAt(orderEntity.getCreatedAt())
            .build();
    }
}
