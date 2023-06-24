package ge.carapp.carappapi.schema.payment;


import ge.carapp.carappapi.models.bog.details.Item;
import ge.carapp.carappapi.models.bog.details.PurchaseUnits;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
public record PurchaseUnitsSchema(
    String requestAmount,
    String transferAmount,
    String refundAmount,
    String currencyCode,
    List<BogItemSchema> items
) {
    public static PurchaseUnitsSchema from(@NotNull PurchaseUnits purchaseUnits) {
        return PurchaseUnitsSchema.builder()
            .requestAmount(purchaseUnits.requestAmount())
            .transferAmount(purchaseUnits.transferAmount())
            .refundAmount(purchaseUnits.refundAmount())
            .currencyCode(purchaseUnits.currencyCode())
            .items(Objects.requireNonNullElse(purchaseUnits.items(), new ArrayList<Item>())
                .stream().map(BogItemSchema::from).toList())
            .build();
    }
}
