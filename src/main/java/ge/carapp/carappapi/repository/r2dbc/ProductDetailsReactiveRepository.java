package ge.carapp.carappapi.repository.r2dbc;

import ge.carapp.carappapi.entity.ProductDetailsEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ProductDetailsReactiveRepository extends R2dbcRepository<ProductDetailsEntity, UUID> {

    Flux<ProductDetailsEntity> findAllByProductId(UUID productId);
 }
