package ge.carapp.carappapi.models.bog.order;

import lombok.Builder;

@Builder
public record Buyer(
    String fullName,
    String maskedEmail,
    String maskedPhone
) {
}
