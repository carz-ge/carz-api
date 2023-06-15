package ge.carapp.carappapi.models.bog.order;

public record Buyer(
    String fullName,
    String maskedEmail,
    String maskedPhone
) {
}
