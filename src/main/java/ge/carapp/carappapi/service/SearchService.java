package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.ProductEntity;
import ge.carapp.carappapi.repository.r2dbc.ProductReactiveRepository;
import ge.carapp.carappapi.repository.r2dbc.ScheduleReactiveRepository;
import ge.carapp.carappapi.schema.CarType;
import ge.carapp.carappapi.schema.ProductSchema;
import ge.carapp.carappapi.schema.graphql.ProductFilterInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final ScheduleReactiveRepository scheduleReactiveRepository;

    private final ProductReactiveRepository productReactiveRepository;


    public Flux<ProductSchema> searchProducts(ProductFilterInput filter) {
        final Flux<ProductEntity> productList = productReactiveRepository.findAllByCategoryId(filter.categoryId());

        Flux<ProductEntity> products = productList;
        if (filter.carType() != null) {
            products = productList.filter(product -> product.getProductDetailsList().stream()
                .anyMatch(detail ->
                    detail.getPricesForCarTypes().stream()
                        .anyMatch(carTypeAndPrice ->
                            carTypeAndPrice.carType().equals(CarType.ALL)
                                || carTypeAndPrice.carType().equals(filter.carType()))));
        }

        if (filter.date() != null && filter.time() != null) {
            products =
                products.filter(product -> {
                    Mono<Boolean> isFreeTimeSlotMono =
                        scheduleReactiveRepository.findAllByProductIdAndSchedulingDateAndSchedulingTimeGreaterThan(
                            product.getId(),
                            filter.date(),
                            filter.time(),
                            product.getCapacity() + 1
                        );

                    Boolean isFreeTimeSlot = isFreeTimeSlotMono.block();
                    log.info("isFreeTimeSlot {}", isFreeTimeSlot);
                    return isFreeTimeSlot != null && isFreeTimeSlot;
                });

        }

        return products.map(ProductSchema::convert);
    }
}
