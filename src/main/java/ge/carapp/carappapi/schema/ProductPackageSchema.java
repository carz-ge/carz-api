package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.ProductDetailsCarPrice;
import ge.carapp.carappapi.entity.ProductPackageEntity;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ProductPackageSchema(UUID id,
                                   UUID productId,

                                   LingualString name,
                                   LingualString description,
                                   List<ProductDetailsCarPrice> pricesForCarTypes,

                                   List<LingualString> availableServices,
                                   List<LingualString> notAvailableServices,
                                   Currency currency,
                                   Integer averageDurationMinutes) {
    public static ProductPackageSchema convert(ProductPackageEntity productPackageEntity) {
        return ProductPackageSchema.builder()
            .id(productPackageEntity.getId())
            .name(new LingualString(productPackageEntity.getName(), productPackageEntity.getNameKa()))
            .description(new LingualString(productPackageEntity.getDescription(), productPackageEntity.getDescriptionKa()))
            .productId(productPackageEntity.getProduct().getId())
            .pricesForCarTypes(productPackageEntity.getPricesForCarTypes())
            .availableServices(productPackageEntity.getAvailableServices())
            .notAvailableServices(productPackageEntity.getNotAvailableServices())
            .currency(productPackageEntity.getCurrency())
            .averageDurationMinutes(productPackageEntity.getAverageDurationMinutes())
            .build();
    }
}
