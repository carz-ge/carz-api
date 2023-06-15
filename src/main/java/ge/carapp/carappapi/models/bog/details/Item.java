package ge.carapp.carappapi.models.bog.details;

public record Item(
    String externalItemID,
    String description,
    String quantity,
    String unitPrice
) {
}
