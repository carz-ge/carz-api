package ge.carapp.carappapi.schema.graphql;

public record ProviderInput(
    String name,
    String phone,
    String email,
    String logo,
    String website
    ) {
}
