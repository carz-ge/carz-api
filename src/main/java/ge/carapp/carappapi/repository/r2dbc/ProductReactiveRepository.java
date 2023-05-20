package ge.carapp.carappapi.repository.r2dbc;

import ge.carapp.carappapi.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ProductReactiveRepository  extends R2dbcRepository<ProductEntity, UUID> {
    Flux<ProductEntity> findAllByCategoryId(UUID categoryId);
}
