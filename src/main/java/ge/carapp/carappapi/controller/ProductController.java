package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.schema.ProductSchema;
import ge.carapp.carappapi.schema.graphql.ProductInput;
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
public class ProductController {
    private final ProductService productService;
    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public ProductSchema getProduct(@Argument UUID productId) {
        return productService.getProductById(productId);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<ProductSchema> listProducts() {
        return productService.getProducts();
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<ProductSchema> listProductByCategoryId(@Argument UUID categoryId) {
        return productService.listProductByCategoryId(categoryId);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public List<ProductSchema> listProductByProviderId(@Argument UUID providerId) {
        return productService.listProductByProviderId(providerId);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ProductSchema createProduct(@Argument ProductInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return productService.createProduct(authenticatedUser, input);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ProductSchema updateProduct(@Argument UUID productId, @Argument ProductInput input) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return productService.updateProduct(authenticatedUser, productId, input);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public boolean removeProduct(@Argument UUID productId) {
        UserEntity authenticatedUser = AuthenticatedUserProvider.getAuthenticatedUser();
        return productService.removeProduct(authenticatedUser, productId);
    }

    @BatchMapping(typeName = "Order")
    public Map<OrderSchema, ProductSchema> product(List<OrderSchema> orders) {
        log.info("product orders: {}", orders);

        Set<UUID> productIds = orders.stream()
            .map(OrderSchema::productId)
            .collect(Collectors.toSet());

        List<ProductSchema> products = productService.batchGetProducts(productIds);
        Map<UUID, ProductSchema> productsMap = products.stream()
            .collect(Collectors.toMap(
                ProductSchema::id,
                Function.identity()
            ));

        return orders.stream().collect(Collectors.toMap(
            Function.identity(),
            o-> productsMap.get(o.productId()))
        );
    }
}
