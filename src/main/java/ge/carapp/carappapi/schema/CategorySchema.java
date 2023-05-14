package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.CategoryEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategorySchema {
    LingualString name;

    public static CategorySchema convert(CategoryEntity categoryEntity) {
        return CategorySchema.builder()
            .name(LingualString.builder()
                .en(categoryEntity.getName())
                .ka(categoryEntity.getNameKa())
                .build())
            .build();
    }
}
