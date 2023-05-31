package ge.carapp.carappapi.repository.r2dbc;

import ge.carapp.carappapi.entity.UserOtpEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface OtpReactiveRepository extends R2dbcRepository<UserOtpEntity, UUID> {
    Mono<UserOtpEntity> findByUserId(UUID userId);
}
