package ge.carapp.carappapi.models.bog.order;

import lombok.Builder;

@Builder
public record OrderLinks(
    BogLink details,
    BogLink redirect
) {
}
