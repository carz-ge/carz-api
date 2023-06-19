package ge.carapp.carappapi.schema.graphql;

import ge.carapp.carappapi.schema.LingualString;
import ge.carapp.carappapi.schema.location.LocationSchema;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record ProductInput(
    @NotNull LingualString name,
    @NotNull UUID categoryId,
    @NotNull UUID providerId,

    LingualString description,
    LocationSchema location,
    String mainImage,
    List<String> images,
    List<String> tags
) {

}
