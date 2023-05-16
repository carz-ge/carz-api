package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.CategoryEntity;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CategorySchema(UUID id, LingualString name) {

    public static CategorySchema convert(CategoryEntity categoryEntity) {
        return CategorySchema.builder()
            .id(categoryEntity.getId())
            .name(new LingualString(categoryEntity.getName(), categoryEntity.getNameKa()))
            .build();
    }
}
