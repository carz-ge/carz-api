package ge.carapp.carappapi.schema.payment;

import ge.carapp.carappapi.models.bog.details.OrderDetails;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record PaymentInfoSchema(
    UUID orderID,
    String industry,
    String capture,
    String externalOrderID,
    PaymentClientSchema client,
    OffsetDateTime createDate,
    OffsetDateTime expireDate,
    KeyValueSchema orderStatus,
    PurchaseUnitsSchema purchaseUnits,
    RedirectLinksSchema redirectLinks,
    PaymentDetailSchema paymentDetail,
    String lang,
    String rejectReason) {

    public static PaymentInfoSchema from(OrderDetails orderDetails) {
        return PaymentInfoSchema.builder()
            .orderID(orderDetails.orderID())
            .industry(orderDetails.industry())
            .capture(orderDetails.capture())
            .externalOrderID(orderDetails.externalOrderID())
            .client(PaymentClientSchema.from(orderDetails.client()))
            .createDate(orderDetails.createDate())
            .expireDate(orderDetails.expireDate())
            .orderStatus(new KeyValueSchema(orderDetails.orderStatus().key(), orderDetails.orderStatus().value()))
            .purchaseUnits(PurchaseUnitsSchema.from(orderDetails.purchaseUnits()))
            .redirectLinks(RedirectLinksSchema.from(orderDetails.redirectLinks()))
            .paymentDetail(PaymentDetailSchema.from(orderDetails.paymentDetail()))
            .lang(orderDetails.lang())
            .rejectReason(orderDetails.rejectReason())
            .build();
    }
}
