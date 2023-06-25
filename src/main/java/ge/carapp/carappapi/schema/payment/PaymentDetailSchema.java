package ge.carapp.carappapi.schema.payment;

import ge.carapp.carappapi.models.bog.details.KeyValue;
import ge.carapp.carappapi.models.bog.details.PaymentDetail;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Objects;

@Builder
public record PaymentDetailSchema(
    KeyValueSchema transferMethod,
    String transactionID,
    String payerIdentifier,
    String paymentOption,
    String cardType,
    String cardExpiryDate) {
    public static PaymentDetailSchema from(@NotNull PaymentDetail paymentDetail) {
        KeyValue keyValue = paymentDetail.transferMethod();
        KeyValueSchema transferMethod = Objects.nonNull(keyValue) ? new KeyValueSchema(keyValue.key(),
            keyValue.value()) : null;
        return PaymentDetailSchema.builder()
            .transferMethod(transferMethod)
            .transactionID(paymentDetail.transactionId())
            .payerIdentifier(paymentDetail.payerIdentifier())
            .paymentOption(paymentDetail.paymentOption())
            .cardType(paymentDetail.cardType())
            .cardExpiryDate(paymentDetail.cardExpiryDate())
            .build();

    }
}
