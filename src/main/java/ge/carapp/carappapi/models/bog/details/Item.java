package ge.carapp.carappapi.models.bog.details;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Item(
    @JsonProperty("external_item_id")
    String externalItemId,
    String description,
    String quantity,
    @JsonProperty("unit_price")
    String unitPrice
) {
}
