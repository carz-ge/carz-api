package ge.carapp.carappapi.repository.r2dbc;

import ge.carapp.carappapi.entity.ScheduleEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Repository
public interface ScheduleReactiveRepository extends R2dbcRepository<ScheduleEntity, UUID> {
    Flux<ScheduleEntity> findAllByProductIdAndSchedulingDateAndSchedulingTime(
        UUID productId, LocalDate schedulingDate, LocalTime schedulingTime);

    Mono<Boolean> findAllByProductIdAndSchedulingDateAndSchedulingTimeGreaterThan(
        UUID productId, LocalDate schedulingDate, LocalTime schedulingTime, int cap);


}
