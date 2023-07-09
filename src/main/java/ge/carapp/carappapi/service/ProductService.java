package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.ProductDetailsCarPrice;
import ge.carapp.carappapi.entity.ProductDetailsEntity;
import ge.carapp.carappapi.entity.ProviderEntity;
import ge.carapp.carappapi.repository.ProductDetailsRepository;
import ge.carapp.carappapi.repository.ProviderRepository;
import ge.carapp.carappapi.schema.graphql.ProductDetailsInput;
import ge.carapp.carappapi.schema.ProductDetailsSchema;
import ge.carapp.carappapi.entity.CategoryEntity;
import ge.carapp.carappapi.entity.ProductEntity;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.repository.CategoryRepository;
import ge.carapp.carappapi.schema.ProductSchema;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.repository.ProductRepository;
import ge.carapp.carappapi.schema.graphql.ProductInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProviderRepository providerRepository;
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

        Optional<ProviderEntity> provider = providerRepository.findById(input.providerId());
        if (provider.isEmpty()) {
            throw new GeneralException("Provider not found");
        }


        ProductEntity productEntity = ProductEntity.builder()
            .category(category.get())
            .provider(provider.get())
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
            .capacity(1)
            .build();

        productEntity = productRepository.save(productEntity);
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
        productEntity.setCapacity(1);

        productEntity = productRepository.save(productEntity);
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
        return productRepository.findAllByProviderId(providerId).stream().map(ProductSchema::convert).toList();
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
        productDetailsEntity = productDetailsRepository.save(productDetailsEntity);

        productEntity.getProductDetailsList().add(productDetailsEntity);

        return ProductDetailsSchema.convert(productDetailsEntity);
    }

    public ProductDetailsSchema updateProductDetails(UserEntity authenticatedUser, UUID productDetailsId, ProductDetailsInput input) {
       // TODO
        return null;
    }

    public boolean removeProductDetails(UserEntity authenticatedUser, UUID productId) {
        // TODO
        return false;
    }

    public ProductSchema getProductById(UUID productId) {
        ProductEntity productEntity = getProductEntity(productId);
        return ProductSchema.convert(productEntity);
    }

    public ProductEntity getProductEntity(UUID productId) {
        return productRepository
            .findById(productId)
            .orElseThrow(()-> new GeneralException("product by id not found"));
    }

    public List<ProductDetailsSchema> batchGetProductDetails(List<UUID> productIds) {
        return productDetailsRepository.findAllByProductIdIn(productIds).stream()
            .map(ProductDetailsSchema::convert).toList();
    }

    public void updateReviewCount(ProductEntity productEntity, @Range(min=0, max=5) int stars) {
        productRepository.updateReviews(productEntity.getId(), stars);
    }
}
