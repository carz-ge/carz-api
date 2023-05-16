package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.ProductDetailsCarPrice;
import ge.carapp.carappapi.entity.ProductDetailsEntity;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ProductDetailsSchema(UUID id,
                                   UUID productId,

                                   LingualString name,
                                   LingualString description,
                                   List<ProductDetailsCarPrice> pricesForCarTypes,

                                   List<LingualString> availableServices,
                                   List<LingualString> notAvailableServices,
                                   Currency currency,
                                   Integer averageDurationMinutes) {
    public static ProductDetailsSchema convert(ProductDetailsEntity productDetailsEntity) {
        return ProductDetailsSchema.builder()
            .id(productDetailsEntity.getId())
            .name(new LingualString(productDetailsEntity.getName(), productDetailsEntity.getNameKa()))
            .description(new LingualString(productDetailsEntity.getDescription(), productDetailsEntity.getDescriptionKa()))
            .productId(productDetailsEntity.getProduct().getId())
            .pricesForCarTypes(productDetailsEntity.getPricesForCarTypes())
            .availableServices(productDetailsEntity.getAvailableServices())
            .notAvailableServices(productDetailsEntity.getNotAvailableServices())
            .currency(productDetailsEntity.getCurrency())
            .averageDurationMinutes(productDetailsEntity.getAverageDurationMinutes())
            .build();
    }
}
