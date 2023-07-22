package ge.carapp.carappapi.service;

import ge.carapp.carappapi.schema.graphql.CategoryInput;
import ge.carapp.carappapi.entity.CategoryEntity;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.repository.CategoryRepository;
import ge.carapp.carappapi.schema.CategorySchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Cacheable("getCategories")
//    @CacheEvict(value = "categories", allEntries = true)
//    @Scheduled(fixedRateString = "${caching.spring.categoriestTTL}")
    public List<CategorySchema> getCategories() {
        List<CategorySchema> categories = categoryRepository.findAllByActiveOrderByPriorityDesc(true).stream().map(CategorySchema::convert).toList();
        log.info("Categories list: {}", categories);
        return categories;
    }

    public CategorySchema createCategory(UserEntity authenticatedUser, CategoryInput input) {
        List<CategoryEntity> allCategories = categoryRepository.findAll();
        Optional<CategoryEntity> filtered = allCategories.stream()
            .filter(category -> category.getName()
                .equals(input.name().en()))
            .findFirst();
        if (filtered.isPresent()) {
            return CategorySchema.convert(filtered.get());
        }

        CategoryEntity category = CategoryEntity.builder()
            .name(input.name().en())
            .nameKa(input.name().ka())
            .internalName(input.internalName())
            .image(input.image())
            .priority(Objects.requireNonNullElse(input.priority(), 0))
            .active(Objects.requireNonNullElse(input.active(), true))
            .build();

        category = categoryRepository.save(category);
        return CategorySchema.convert(category);
    }

    public CategorySchema updateCategory(UserEntity authenticatedUser, UUID categoryId, CategoryInput input) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);
        if (categoryEntity.isEmpty()) {
            return null;
        }
        CategoryEntity category = categoryEntity.get();
        category.setName(input.name().en());
        category.setNameKa(input.name().ka());

        category = categoryRepository.save(category);
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

    public List<CategorySchema> batchGetCategories(Set<UUID> categoryIds) {
        return categoryRepository.findAllById(categoryIds).stream()
            .map(CategorySchema::convert).toList();
    }
}
