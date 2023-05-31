package ge.carapp.carappapi.repository.r2dbc;

import ge.carapp.carappapi.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ProductReactiveRepository extends R2dbcRepository<ProductEntity, UUID> {
    Flux<ProductEntity> findAllByCategoryId(UUID categoryId);
    Flux<ProductEntity> findAllByProviderId(UUID providerId);

}
