package ge.carapp.carappapi.repository.r2dbc;

import ge.carapp.carappapi.entity.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserReactiveRepository extends R2dbcRepository<UserEntity, UUID> {
    Mono<UserEntity> findByPhone(String phone);
}
