package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.CategoryEntity;
import ge.carapp.carappapi.entity.ProductEntity;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.repository.CategoryRepository;
import ge.carapp.carappapi.schema.graphql.ProductFilterInput;
import ge.carapp.carappapi.schema.ProductSchema;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.repository.ProductRepository;
import ge.carapp.carappapi.schema.graphql.ProductInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductSchema> getProducts() {
        return productRepository.findAll().stream().map(ProductSchema::convert).toList();
    }

    public ProductSchema createProduct(UserEntity authenticatedUser, ProductInput input) {
        Optional<CategoryEntity> category = categoryRepository.findById(input.categoryId());
        if (category.isEmpty()) {
            throw new GeneralException("Category not found");
        }


        ProductEntity productEntity = ProductEntity.builder()
            .category(category.get())
            .name(input.name().en())
            .nameKa(input.name().ka())
            .description(input.description().en())
            .descriptionKa(input.description().ka())
            .mainImage(input.mainImage())
            .images(input.images())
            .tags(input.tags())
            .city(input.location().address().city())
            .district(input.location().address().district())
            .street(input.location().address().street())
            .lat(input.location().coordinates().lat())
            .lng(input.location().coordinates().lng())
            .build();

        productRepository.save(productEntity);

        return ProductSchema.convert(productEntity);
    }

    public ProductSchema updateProduct(UserEntity authenticatedUser, UUID productId, ProductInput input) {
        Optional<ProductEntity> productEntityOptional = productRepository.findById(productId);
        if (productEntityOptional.isEmpty()) {
            throw new GeneralException("Product not found");
        }


        ProductEntity productEntity = productEntityOptional.get();
        productEntity.setName(input.name().en());
        productEntity.setNameKa(input.name().ka());
        productEntity.setDescription(input.description().en());
        productEntity.setDescriptionKa(input.description().ka());
        productEntity.setMainImage(input.mainImage());
        productEntity.setImages(input.images());
        productEntity.setTags(input.tags());
        productEntity.setCity(input.location().address().city());
        productEntity.setDistrict(input.location().address().district());
        productEntity.setStreet(input.location().address().street());
        productEntity.setLat(input.location().coordinates().lat());
        productEntity.setLng(input.location().coordinates().lng());
        productRepository.save(productEntity);
        return ProductSchema.convert(productEntity);
    }

    public boolean removeProduct(UserEntity authenticatedUser, UUID productId) {
        productRepository.deleteById(productId);
        return true;
    }

    public List<ProductSchema> listProductByCategoryId(UUID categoryId) {
        return productRepository.findAllByCategoryId(categoryId).stream().map(ProductSchema::convert).toList();
    }

    public List<ProductSchema> listProductByProviderId(UUID providerId) {
        return null;
    }

    public List<ProductSchema> filterProducts(ProductFilterInput filter) {
        // TODOOOOOOOO
        return null;
    }
}
