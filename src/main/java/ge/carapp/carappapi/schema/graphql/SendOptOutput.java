package ge.carapp.carappapi.schema.graphql;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SendOptOutput(
    boolean sent,
    LocalDateTime expiresAt,
    boolean isRegistered
) {
}
