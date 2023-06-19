package ge.carapp.carappapi.schema.graphql;

import jakarta.validation.constraints.NotEmpty;

public record ProviderInput(
    @NotEmpty String name,
    @NotEmpty String phone,
    String email,
    String logo,
    String website
) {
}
