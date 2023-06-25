package ge.carapp.carappapi.schema.payment;

import ge.carapp.carappapi.models.bog.details.Item;
import lombok.Builder;

@Builder
public record BogItemSchema(
    String externalItemID,
    String description,
    String quantity,
    String unitPrice
) {
    public static BogItemSchema from(Item item) {
        return BogItemSchema.builder()
            .externalItemID(item.externalItemId())
            .description(item.description())
            .quantity(item.quantity())
            .unitPrice(item.unitPrice())
            .build();
    }
}
