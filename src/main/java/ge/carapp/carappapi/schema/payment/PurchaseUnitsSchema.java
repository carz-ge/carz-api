package ge.carapp.carappapi.schema.payment;


import ge.carapp.carappapi.models.bog.details.PurchaseUnits;
import lombok.Builder;

import java.util.List;

@Builder
public record PurchaseUnitsSchema(
    String requestAmount,
    String transferAmount,
    String refundAmount,
    String currencyCode,
    List<BogItemSchema> items
) {
    public static PurchaseUnitsSchema from(PurchaseUnits purchaseUnits) {
        return PurchaseUnitsSchema.builder()
            .requestAmount(purchaseUnits.requestAmount())
            .transferAmount(purchaseUnits.transferAmount())
            .refundAmount(purchaseUnits.refundAmount())
            .currencyCode(purchaseUnits.currencyCode())
            .items(purchaseUnits.items().stream().map(BogItemSchema::from).toList())
            .build();
    }
}
