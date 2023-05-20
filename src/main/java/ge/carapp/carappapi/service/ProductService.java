package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.ProductDetailsCarPrice;
import ge.carapp.carappapi.entity.ProductDetailsEntity;
import ge.carapp.carappapi.repository.jpa.ProductDetailsRepository;
import ge.carapp.carappapi.schema.graphql.ProductDetailsInput;
import ge.carapp.carappapi.schema.ProductDetailsSchema;
import ge.carapp.carappapi.entity.CategoryEntity;
import ge.carapp.carappapi.entity.ProductEntity;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.repository.jpa.CategoryRepository;
import ge.carapp.carappapi.schema.graphql.ProductFilterInput;
import ge.carapp.carappapi.schema.ProductSchema;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.repository.jpa.ProductRepository;
import ge.carapp.carappapi.schema.graphql.ProductInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductDetailsRepository productDetailsRepository;

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


    public List<ProductDetailsSchema> getProductDetailsByProductId(UUID productId) {

        return productDetailsRepository.findAllByProductId(productId).stream().map(ProductDetailsSchema::convert).toList();
    }

    public ProductDetailsSchema createProductDetails(UserEntity authenticatedUser, ProductDetailsInput input) {
        Optional<ProductEntity> productEntityOpt = productRepository.findById(input.productId());
        if (productEntityOpt.isEmpty()) {
            throw new GeneralException("Product not found");
        }
        ProductEntity productEntity = productEntityOpt.get();
        List<ProductDetailsEntity> productDetailsList = productEntity.getProductDetailsList();

        // check if product details with same name already exists
        if (productDetailsList.stream()
            .anyMatch(productDetailsEntity -> productDetailsEntity.getName().equals(input.name().en()))) {
            throw new GeneralException("Product details with same name already exists");
        }
        var convertedCarType = IntStream.range(0, input.pricesForCarTypes().size())
            .mapToObj(i -> {
                var carTypeAndPrice = input.pricesForCarTypes().get(i);
                return new ProductDetailsCarPrice(
                    i,
                    carTypeAndPrice.carType(),
                    carTypeAndPrice.price());
            }).toList();

        // create product details and add them to the product details list
        ProductDetailsEntity productDetailsEntity = ProductDetailsEntity.builder()
            .product(productEntity)
            .name(input.name().en())
            .nameKa(input.name().ka())
            .description(input.description().en())
            .descriptionKa(input.description().ka())
            .availableServices(input.availableServices())
            .notAvailableServices(input.notAvailableServices())
            .pricesForCarTypes(convertedCarType)
            .currency(input.currency())
            .averageDurationMinutes(input.averageDurationMinutes())
            .build();
        productDetailsRepository.save(productDetailsEntity);

        productEntity.getProductDetailsList().add(productDetailsEntity);

        return ProductDetailsSchema.convert(productDetailsEntity);
    }

    public ProductSchema updateProductDetails(UserEntity authenticatedUser, UUID productDetailsId, ProductDetailsInput input) {
        return null;
    }

    public boolean removeProductDetails(UserEntity authenticatedUser, UUID productId) {
        return false;
    }
}
