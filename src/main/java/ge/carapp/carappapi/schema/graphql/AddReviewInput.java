package ge.carapp.carappapi.schema.graphql;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.util.UUID;

public record AddReviewInput(
    @NotNull UUID productId,
    @Range(min=0, max=5) int stars,
    String comment
) {
}
