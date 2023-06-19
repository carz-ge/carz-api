package ge.carapp.carappapi.schema.graphql;

import jakarta.validation.constraints.NotEmpty;

public record PushNotificationInput(
    @NotEmpty String deviceToken,
    @NotEmpty String title,
    @NotEmpty String text
) {
}
