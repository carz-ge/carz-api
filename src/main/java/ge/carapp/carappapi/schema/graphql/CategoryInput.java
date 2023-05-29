package ge.carapp.carappapi.schema.graphql;

import ge.carapp.carappapi.schema.LingualString;

public record CategoryInput(
    LingualString name,
    String internalName,
    String image,
    Integer priority,
    Boolean active
) {
}
