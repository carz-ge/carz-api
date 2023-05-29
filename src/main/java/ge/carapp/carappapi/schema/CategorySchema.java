package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.CategoryEntity;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CategorySchema(
    UUID id,
    LingualString name,
    String internalName,
    String image,
    int priority,
    boolean active
) {

    public static CategorySchema convert(CategoryEntity categoryEntity) {
        return CategorySchema.builder()
            .id(categoryEntity.getId())
            .name(new LingualString(categoryEntity.getName(), categoryEntity.getNameKa()))
            .internalName(categoryEntity.getInternalName())
            .image(categoryEntity.getImage())
            .priority(categoryEntity.getPriority())
            .active(categoryEntity.isActive())
            .build();
    }
}
