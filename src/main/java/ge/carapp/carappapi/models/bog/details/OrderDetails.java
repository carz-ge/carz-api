package ge.carapp.carappapi.models.bog.details;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderDetails(
    @JsonProperty("order_id")
    UUID orderId,
    String industry,
    String capture,
    @JsonProperty("external_order_id")
    String externalOrderId,
    Client client,
    @JsonProperty("create_date")
    OffsetDateTime createDate,
    @JsonProperty("expire_date")
    OffsetDateTime expireDate,
    @JsonProperty("order_status")
    KeyValue orderStatus,
    @JsonProperty("purchase_units")
    PurchaseUnits purchaseUnits,
    @JsonProperty("redirect_links")
    RedirectLinks redirectLinks,
    @JsonProperty("payment_detail")
    PaymentDetail paymentDetail,
    String lang,
    @JsonProperty("reject_reason")
    String rejectReason) {

}

