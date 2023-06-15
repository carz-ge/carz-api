package ge.carapp.carappapi.models.bog.details;

public record PaymentDetail(
    OrderStatus transferMethod,
    String transactionID,
    String payerIdentifier,
    String paymentOption,
    String cardType,
    String cardExpiryDate
) {
}
