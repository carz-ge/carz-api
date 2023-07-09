package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.ProductEntity;
import ge.carapp.carappapi.repository.ProductRepository;
import ge.carapp.carappapi.repository.ScheduleRepository;
import ge.carapp.carappapi.schema.CarType;
import ge.carapp.carappapi.schema.ProductSchema;
import ge.carapp.carappapi.schema.graphql.ProductFilterInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {
    private final ScheduleRepository scheduleRepository;

    private final ProductRepository productRepository;

    //TODO: MAKE reactive
    public List<ProductSchema> searchProducts(ProductFilterInput filter) {
        log.info("searching: {}", filter);
        List<ProductEntity> productList;
        if (Objects.nonNull(filter.categoryId())){
            productList = productRepository.findAllByCategoryId(filter.categoryId());
        } else {
            productList = productRepository.findAll();
        }

        Stream<ProductEntity> products = productList.stream();
        if (filter.carType() != null) {
            products = products.filter(product -> product.getProductDetailsList().stream()
                .anyMatch(detail ->
                    detail.getPricesForCarTypes().stream()
                        .anyMatch(carTypeAndPrice ->
                            carTypeAndPrice.carType().equals(CarType.ALL)
                                || carTypeAndPrice.carType().equals(filter.carType()))));
        }
        // TODO filter separately
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
        List<ProductSchema> list = products.map(ProductSchema::convert).toList();
        log.info("search products: {}, filter: {}", list, filter);
        return list;
    }
}
