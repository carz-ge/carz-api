package ge.carapp.carappapi.schema.graphql;

import jakarta.validation.constraints.NotEmpty;

public record AddDeviceTokenInput(
    @NotEmpty String deviceToken,
    @NotEmpty String platform
) {
}
