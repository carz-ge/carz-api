package ge.carapp.carappapi.models.bog.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record OrderRequest(
    @JsonProperty("callback_url")
    @NotNull String callbackUrl,
    @NotNull PurchaseInfo purchaseUnits,
    String externalOrderId,

    RedirectUrls redirectUrls,

    Buyer buyer,

    String capture, //  "automatic" | "manual"
    Integer ttl,
    List<String> paymentMethod // google_pay, bog_p2p, bog_loyalty, bnpl, bog_loan
//    PaymentConfiguration config // we dont need this
) {
}
