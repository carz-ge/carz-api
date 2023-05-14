package ge.carapp.carappapi.service;

import ge.carapp.carappapi.schema.graphql.ProductFilterInput;
import ge.carapp.carappapi.schema.ProductSchema;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.repository.ProductRepository;
import ge.carapp.carappapi.schema.graphql.ProductInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public List<ProductSchema> getProducts() {
        return null;
    }

    public ProductSchema createProduct(UserEntity authenticatedUser, ProductInput input) {
        return null;
    }

    public ProductSchema updateProduct(UserEntity authenticatedUser, UUID productId, ProductInput input) {
        return null;
    }

    public boolean removeProduct(UserEntity authenticatedUser, UUID productId) {
        return false;
    }

    public List<ProductSchema> listProductByCategoryId(UUID categoryId) {
        return null;
    }

    public List<ProductSchema> listProductByProviderId(UUID providerId) {
        return null;
    }

    public List<ProductSchema> filterProducts(ProductFilterInput filter) {
        return null;
    }
}
