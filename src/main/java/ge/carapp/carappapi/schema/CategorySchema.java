package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.CategoryEntity;
import lombok.Builder;

@Builder
public record CategorySchema(LingualString name) {

    public static CategorySchema convert(CategoryEntity categoryEntity) {
        return CategorySchema.builder()
            .name(new LingualString(categoryEntity.getName(), categoryEntity.getNameKa()))
            .build();
    }
}
