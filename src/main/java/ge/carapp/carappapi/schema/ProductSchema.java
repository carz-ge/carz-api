package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.schema.location.LocationSchema;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ProductSchema {
    UUID id;
    LingualString name;
    UUID categoryId;
    LingualString description;
    LocationSchema location;
    String mainImage;
    List<String> images;
    List<String> tags;

    Double reviewRate;
    int reviewCount;
}
