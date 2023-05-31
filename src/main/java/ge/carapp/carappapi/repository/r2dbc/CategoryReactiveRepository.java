package ge.carapp.carappapi.repository.r2dbc;

import ge.carapp.carappapi.entity.CategoryEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface CategoryReactiveRepository extends R2dbcRepository<CategoryEntity, UUID> {

    Flux<CategoryEntity> findAllByActiveOrderByPriorityDesc(Boolean active);
}
