package ge.carapp.carappapi.schema.graphql;

import ge.carapp.carappapi.schema.LingualString;
import ge.carapp.carappapi.schema.location.LocationSchema;

import java.util.List;
import java.util.UUID;

public record ProductInput(
    LingualString name,
    UUID categoryId,
    LingualString description,
    LocationSchema location,
    String mainImage,
    List<String> images,
    List<String> tags
) {

}
