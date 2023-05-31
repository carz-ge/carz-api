package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.ProductDetailsSchema;
import ge.carapp.carappapi.schema.ProductSchema;
import ge.carapp.carappapi.schema.graphql.ProductDetailsInput;
import ge.carapp.carappapi.security.AuthenticatedUserProvider;
import ge.carapp.carappapi.service.ProductService;
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
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductDetailsController {
    private final ProductService productService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<ProductDetailsSchema> listProductDetailsByProductId(@Argument UUID productId) {
        return productService.getProductDetailsByProductId(productId);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ProductDetailsSchema createProductDetails(@Argument ProductDetailsInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return productService.createProductDetails(authenticatedUser, input);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ProductDetailsSchema updateProductDetails(@Argument UUID productDetailsId, @Argument ProductDetailsInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return productService.updateProductDetails(authenticatedUser, productDetailsId, input);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public boolean removeProductDetails(@Argument UUID productId) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return productService.removeProductDetails(authenticatedUser, productId);
    }

    @BatchMapping(typeName = "Product")
    public Map<ProductSchema, List<ProductDetailsSchema>> packages(List<ProductSchema> products) {
        log.info("products: {}", products);

        var productIds = products.stream()
            .map(ProductSchema::id)
            .toList();

        List<ProductDetailsSchema> packagesList = productService.batchGetProductDetails(productIds);
        log.info("packagesList: {}", packagesList);
        Map<UUID, List<ProductDetailsSchema>> groupedPackages = packagesList.stream()
            .collect(Collectors.groupingBy(ProductDetailsSchema::productId));

        Map<ProductSchema, List<ProductDetailsSchema>> mappedResult = products.stream()
            .collect(Collectors.toMap(product -> product,
                product -> groupedPackages.get(product.id())));

        log.info("mappedResult: {}", mappedResult);
        return mappedResult;
    }

}
