package ge.carapp.carappapi.service;

import ge.carapp.carappapi.schema.graphql.CategoryInput;
import ge.carapp.carappapi.entity.CategoryEntity;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.repository.CategoryRepository;
import ge.carapp.carappapi.schema.CategorySchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategorySchema> getCategories() {
        return categoryRepository.findAll().stream().map(CategorySchema::convert).toList();
    }

    public CategorySchema createCategory(UserEntity authenticatedUser, CategoryInput input) {
        List<CategoryEntity> allCategories = categoryRepository.findAll();
        Optional<CategoryEntity> filtered = allCategories.stream()
            .filter(category -> category.getName()
                .equals(input.name().getEn()))
            .findFirst();
        if (filtered.isPresent()) {
            return CategorySchema.convert(filtered.get());
        }

        CategoryEntity category = CategoryEntity.builder()
            .name(input.name().getEn())
            .nameKa(input.name().getKa())
            .build();

        categoryRepository.save(category);
        return CategorySchema.convert(category);
    }

    public CategorySchema updateCategory(UserEntity authenticatedUser, UUID categoryId, CategoryInput input) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);
        if (categoryEntity.isEmpty()) {
            return null;
        }
        CategoryEntity category = categoryEntity.get();
        category.setName(input.name().getEn());
        category.setNameKa(input.name().getKa());
        categoryRepository.save(category);
        return CategorySchema.convert(category);
    }

    public boolean removeCategory(UserEntity authenticatedUser, UUID categoryId) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);
        if (categoryEntity.isEmpty()) {
            return false;
        }
        categoryRepository.delete(categoryEntity.get());
        return true;
    }
}
