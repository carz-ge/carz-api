package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.ProductEntity;
import ge.carapp.carappapi.repository.jpa.ProductRepository;
import ge.carapp.carappapi.repository.jpa.ScheduleRepository;
import ge.carapp.carappapi.schema.CarType;
import ge.carapp.carappapi.schema.ProductSchema;
import ge.carapp.carappapi.schema.graphql.ProductFilterInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final ScheduleRepository scheduleRepository;

    private final ProductRepository productRepository;

    //TODO: MAKE reactive
    public List<ProductSchema> searchProducts(ProductFilterInput filter) {
        final List<ProductEntity> productList = productRepository.findAllByCategoryId(filter.categoryId());

        Stream<ProductEntity> products = productList.stream();
        if (filter.carType() != null) {
            products = products.filter(product -> product.getProductDetailsList().stream()
                .anyMatch(detail ->
                    detail.getPricesForCarTypes().stream()
                        .anyMatch(carTypeAndPrice ->
                            carTypeAndPrice.carType().equals(CarType.ALL)
                                || carTypeAndPrice.carType().equals(filter.carType()))));
        }

        if (filter.date() != null && filter.time() != null) {
            products =
                products.filter(product -> {
                    Boolean isFreeTimeSlot =
                        scheduleRepository.hasCapacity(
                            product.getId(),
                            filter.date(),
                            filter.time(),
                            product.getCapacity()
                        );

                    log.info("isFreeTimeSlot {}", isFreeTimeSlot);
                    return isFreeTimeSlot != null && isFreeTimeSlot;
                });

        }

        return products.map(ProductSchema::convert).toList();
    }
}
