package ge.carapp.carappapi.schema.graphql;

import ge.carapp.carappapi.schema.LingualString;
import jakarta.validation.constraints.NotNull;

public record CategoryInput(
    @NotNull LingualString name,
    @NotNull String internalName,
    String image,
    Integer priority,
    Boolean active
) {
}
