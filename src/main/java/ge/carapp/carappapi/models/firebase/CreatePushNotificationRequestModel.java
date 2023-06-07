package ge.carapp.carappapi.models.firebase;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record CreatePushNotificationRequestModel(
    @NotNull
    String title,
    @NotNull
    String text,
    @NotNull
    String token
) {
}
