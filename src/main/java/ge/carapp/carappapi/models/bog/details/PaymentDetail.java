package ge.carapp.carappapi.models.bog.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record PaymentDetail(
    @JsonProperty("transfer_method")
    KeyValue transferMethod,
    @JsonProperty("transaction_id")
    String transactionId,
    @JsonProperty("payer_identifier")
    String payerIdentifier,
    @JsonProperty("payment_option")
    String paymentOption,
    @JsonProperty("card_type")
    String cardType,
    @JsonProperty("card_expiry_date")
    String cardExpiryDate
) {
}
