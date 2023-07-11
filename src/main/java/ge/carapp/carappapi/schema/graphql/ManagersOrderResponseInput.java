package ge.carapp.carappapi.schema.graphql;

import java.util.UUID;

public record ManagersOrderResponseInput(
    UUID orderId,
    boolean accepted
) {
}
