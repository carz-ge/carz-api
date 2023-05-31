package ge.carapp.carappapi.repository.r2dbc;

import ge.carapp.carappapi.entity.ProviderEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ProviderReactiveRepository extends R2dbcRepository<ProviderEntity, UUID> {
    Mono<ProviderEntity> findByName(String name);
}
