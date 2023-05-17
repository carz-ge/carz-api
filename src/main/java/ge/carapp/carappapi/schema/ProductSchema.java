package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.ProductEntity;
import ge.carapp.carappapi.schema.location.Address;
import ge.carapp.carappapi.schema.location.Coordinates;
import ge.carapp.carappapi.schema.location.LocationSchema;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ProductSchema(
    UUID id,
    LingualString name,
    UUID categoryId,
    UUID providerId,
    LingualString description,
    LocationSchema location,
    String mainImage,
    List<String> images,
    List<String> tags
) {
    public static ProductSchema convert(ProductEntity productEntity) {
        LocationSchema location = new LocationSchema(
            new Address(productEntity.getStreet(),
                productEntity.getDistrict(),
                productEntity.getCity()),
            new Coordinates(productEntity.getLat(),
                productEntity.getLng())
        );


        return ProductSchema.builder()
            .id(productEntity.getId())
            .name(new LingualString(productEntity.getName(), productEntity.getNameKa()))
            .description(new LingualString(productEntity.getDescription(), productEntity.getDescriptionKa()))
            .categoryId(productEntity.getCategory().getId())
            .name(new LingualString(productEntity.getDescription(), productEntity.getDescriptionKa()))
            .location(location)
            .mainImage(productEntity.getMainImage())
            .images(productEntity.getImages())
            .tags(productEntity.getTags())
            .build();
    }
}
