package ge.carapp.carappapi.schema.payment;

import ge.carapp.carappapi.models.bog.details.Client;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PaymentClientSchema(
    String id,
    String brandKa,
    String brandEn,
    String url
) {
    public static PaymentClientSchema from(@NotNull Client client) {
        return PaymentClientSchema.builder()
            .id(client.id())
            .brandKa(client.brandKa())
            .brandEn(client.brandEn())
            .url(client.url())
            .build();
    }
}
