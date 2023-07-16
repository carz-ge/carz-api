package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.ProductPackageSchema;
import ge.carapp.carappapi.schema.ProductSchema;
import ge.carapp.carappapi.schema.graphql.ProductDetailsInput;
import ge.carapp.carappapi.schema.order.OrderSchema;
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
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductPackageController {
    private final ProductService productService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<ProductPackageSchema> listProductDetailsByProductId(@Argument UUID productId) {
        return productService.getProductDetailsByProductId(productId);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ProductPackageSchema createProductDetails(@Argument ProductDetailsInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return productService.createProductDetails(authenticatedUser, input);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ProductPackageSchema updateProductDetails(@Argument UUID productDetailsId, @Argument ProductDetailsInput input) {
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
    public Map<ProductSchema, List<ProductPackageSchema>> packages(List<ProductSchema> products) {
        log.info("products: {}", products);

        var productIds = products.stream()
            .map(ProductSchema::id)
            .toList();

        List<ProductPackageSchema> packagesList = productService.batchGetProductPackages(productIds);
        log.info("packagesList: {}", packagesList);
        Map<UUID, List<ProductPackageSchema>> groupedPackages = packagesList.stream()
            .collect(Collectors.groupingBy(ProductPackageSchema::productId));

        Map<ProductSchema, List<ProductPackageSchema>> mappedResult = products.stream()
            .collect(Collectors.toMap(product -> product,
                product -> groupedPackages.get(product.id())));

        log.info("mappedResult: {}", mappedResult);
        return mappedResult;
    }
    @BatchMapping(typeName = "Order")
    public Map<OrderSchema, ProductPackageSchema> productPackage(List<OrderSchema> orders) {
        log.info("productPackage orders: {}", orders);

        Set<UUID> packageIds = orders.stream()
            .map(OrderSchema::packageId)
            .collect(Collectors.toSet());

        List<ProductPackageSchema> packages = productService.batchGetPackages(packageIds);
        Map<UUID, ProductPackageSchema> packagesMap = packages.stream()
            .collect(Collectors.toMap(
                ProductPackageSchema::id,
                Function.identity()
            ));

        return orders.stream().collect(Collectors.toMap(
            Function.identity(),
            o-> packagesMap.get(o.packageId()))
        );
    }

}
