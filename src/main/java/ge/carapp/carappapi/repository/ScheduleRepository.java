package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, UUID> {


    @Query("select case when (count(schedule) >= :capacity)  then false else true end  \n" +
        "from ScheduleEntity schedule " +
        "where schedule.productId = :productId " +
        "and schedule.status = 'PENDING' " +
        "and schedule.schedulingDate = :date " +
        "and schedule.schedulingTime = :time")
    Boolean hasCapacity(
        UUID productId,
        LocalDate date,
        LocalTime time,
        int capacity);
//
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

    @Query("select schedule " +
        "from ScheduleEntity schedule " +
        "where schedule.productId = :productId " +
        "and schedule.status = 'PENDING' " +
        "and schedule.schedulingDate = :date " +
        "and schedule.schedulingTime <= :time")
    List<ScheduleEntity> listAllPendingSchedule(UUID productId, LocalDate date, LocalTime time);

}
