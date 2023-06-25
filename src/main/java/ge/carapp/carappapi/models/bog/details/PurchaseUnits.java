package ge.carapp.carappapi.models.bog.details;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PurchaseUnits(
    @JsonProperty("request_amount")
    String requestAmount,
    @JsonProperty("transfer_amount")
    String transferAmount,
    @JsonProperty("refund_amount")
    String refundAmount,
    @JsonProperty("currency_code")
    String currencyCode,
    List<Item> items
) {

}
