package ge.carapp.carappapi.schema.graphql;

import jakarta.validation.constraints.NotEmpty;

public record SmsNotificationInput(
    @NotEmpty String phone,
    @NotEmpty String text
) {
}
