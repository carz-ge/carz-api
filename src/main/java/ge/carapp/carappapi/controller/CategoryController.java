package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.CategorySchema;
import ge.carapp.carappapi.schema.ProductSchema;
import ge.carapp.carappapi.schema.graphql.CategoryInput;
import ge.carapp.carappapi.security.AuthenticatedUserProvider;
import ge.carapp.carappapi.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<CategorySchema> listCategories() {
        return categoryService.getCategories();
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CategorySchema createCategory(@Argument CategoryInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return categoryService.createCategory(authenticatedUser, input);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public CategorySchema updateCategory(@Argument UUID categoryId, @Argument CategoryInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return categoryService.updateCategory(authenticatedUser, categoryId, input);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public boolean removeCategory(@Argument UUID categoryId) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return categoryService.removeCategory(authenticatedUser, categoryId);
    }

    @BatchMapping(typeName = "Product")
    public Map<ProductSchema, CategorySchema> category(List<ProductSchema> products) {
        log.info("category products: {}", products);

        Set<UUID> categoryIds = products.stream()
            .map(ProductSchema::categoryId)
            .collect(Collectors.toSet());

        List<CategorySchema> categories = categoryService.batchGetCategories(categoryIds);
        Map<UUID, CategorySchema> categoriesMap = categories.stream()
            .collect(Collectors.toMap(
                CategorySchema::id,
                Function.identity()
            ));

        return products.stream().collect(Collectors.toMap(
            Function.identity(),
            o -> categoriesMap.get(o.categoryId()))
        );
    }
}
