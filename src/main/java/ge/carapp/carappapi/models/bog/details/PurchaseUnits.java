package ge.carapp.carappapi.models.bog.details;

import java.util.List;

public record PurchaseUnits(
    String requestAmount,
    String transferAmount,
    String refundAmount,
    String currencyCode,
    List<Item> items
) {

}
