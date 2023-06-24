package ge.carapp.carappapi.models.bog.details;


import java.time.OffsetDateTime;
import java.util.UUID;

public record OrderDetails(
    UUID orderID,
    String industry,
    String capture,
    String externalOrderID,
    Client client,
    OffsetDateTime createDate,
    OffsetDateTime expireDate,
    KeyValue orderStatus,
    PurchaseUnits purchaseUnits,
    RedirectLinks redirectLinks,
    PaymentDetail paymentDetail,
    String lang,
    String rejectReason) {

}

