package ge.carapp.carappapi.schema.payment;

import ge.carapp.carappapi.models.bog.details.PaymentDetail;
import lombok.Builder;

@Builder
public record PaymentDetailSchema(
    KeyValueSchema transferMethod,
    String transactionID,
    String payerIdentifier,
    String paymentOption,
    String cardType,
    String cardExpiryDate) {
    public static PaymentDetailSchema from(PaymentDetail paymentDetail) {
        return PaymentDetailSchema.builder()
            .transferMethod(new KeyValueSchema(paymentDetail.transferMethod().key(), paymentDetail.transferMethod().key()))
            .transactionID(paymentDetail.transactionID())
            .payerIdentifier(paymentDetail.payerIdentifier())
            .paymentOption(paymentDetail.paymentOption())
            .cardType(paymentDetail.cardType())
            .cardExpiryDate(paymentDetail.cardExpiryDate())
            .build();

    }
}
