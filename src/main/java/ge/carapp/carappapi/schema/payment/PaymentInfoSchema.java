package ge.carapp.carappapi.schema.payment;

import ge.carapp.carappapi.models.bog.details.KeyValue;
import ge.carapp.carappapi.models.bog.details.OrderDetails;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Builder
public record PaymentInfoSchema(
    UUID orderId,
    String industry,
    String capture,
    String externalOrderId,
    PaymentClientSchema client,
    OffsetDateTime createDate,
    OffsetDateTime expireDate,
    KeyValueSchema orderStatus,
    PurchaseUnitsSchema purchaseUnits,
    RedirectLinksSchema redirectLinks,
    PaymentDetailSchema paymentDetail,
    String lang,
    String rejectReason) {

    public static PaymentInfoSchema from(@NotNull OrderDetails orderDetails) {
        KeyValue keyValue = orderDetails.orderStatus();
        KeyValueSchema convertedOrderStatus = Objects.nonNull(keyValue) ? new KeyValueSchema(keyValue.key(),
            keyValue.value()) : null;
        return PaymentInfoSchema.builder()
            .orderId(orderDetails.orderId())
            .industry(orderDetails.industry())
            .capture(orderDetails.capture())
            .externalOrderId(orderDetails.externalOrderId())
            .client(Objects.nonNull(orderDetails.client()) ? PaymentClientSchema.from(orderDetails.client()) : null)
            .createDate(orderDetails.createDate())
            .expireDate(orderDetails.expireDate())
            .orderStatus(convertedOrderStatus)
            .purchaseUnits(Objects.nonNull(orderDetails.purchaseUnits()) ? PurchaseUnitsSchema.from(orderDetails.purchaseUnits()) : null)
            .redirectLinks(Objects.nonNull(orderDetails.redirectLinks()) ? RedirectLinksSchema.from(orderDetails.redirectLinks()) : null)
            .paymentDetail(Objects.nonNull(orderDetails.paymentDetail()) ? PaymentDetailSchema.from(orderDetails.paymentDetail()) : null)
            .lang(orderDetails.lang())
            .rejectReason(orderDetails.rejectReason())
            .build();
    }
}
