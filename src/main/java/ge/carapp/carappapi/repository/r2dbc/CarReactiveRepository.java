package ge.carapp.carappapi.repository.r2dbc;

import ge.carapp.carappapi.entity.CarEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface CarReactiveRepository extends R2dbcRepository<CarEntity, UUID> {

    Flux<CarEntity> findAllByOwnerId(UUID ownerId);
}
