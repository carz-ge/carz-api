package ge.carapp.carappapi.models.bog.details;

public record PaymentDetail(
    KeyValue transferMethod,
    String transactionID,
    String payerIdentifier,
    String paymentOption,
    String cardType,
    String cardExpiryDate
) {
}
