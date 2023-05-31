package ge.carapp.carappapi.repository.r2dbc;

import ge.carapp.carappapi.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface ScheduleReactiveRepository extends R2dbcRepository<ScheduleEntity, UUID> {


//    @Query("select case when (count(schedule) >= :capacity)  then false else true end  \n" +
//        "from ScheduleEntity schedule " +
//        "where schedule.productId = :productId " +
//        "and schedule.status = 'PENDING' " +
//        "and schedule.schedulingDate = :date " +
//        "and schedule.schedulingTime = :time")
//    Mono<Boolean> hasCapacity(
//        UUID productId,
//        LocalDate date,
//        LocalTime time,
//        int capacity);
////
//    List<ScheduleEntity> findAllByProductIdAndSchedulingDateAndSchedulingTime(
//        UUID productId,
//        LocalDate date,
//        LocalTime time
//    );
//
//    List<ScheduleEntity> findAllByProductIdAndSchedulingDateAndSchedulingTimeBefore(
//        UUID productId,
//        LocalDate date,
//        LocalTime endTime
//    );

//    @Query("select schedule " +
//        "from ScheduleEntity schedule " +
//        "where schedule.productId = :productId " +
//        "and schedule.status = 'PENDING' " +
//        "and schedule.schedulingDate = :date " +
//        "and schedule.schedulingTime <= :time")
//    Flux<ScheduleEntity> listAllPendingSchedule(UUID productId, LocalDate date, LocalTime time);

}
