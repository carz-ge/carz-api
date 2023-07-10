package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {


    @Query("select case when (count(booking) >= :capacity)  then false else true end  \n" +
        "from BookingEntity booking " +
        "where booking.productId = :productId " +
        "and booking.status = 'PENDING' " +
        "and booking.schedulingDate = :date " +
        "and booking.schedulingTime = :time")
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

    @Query("select booking " +
        "from BookingEntity booking " +
        "where booking.productId = :productId " +
        "and booking.status = 'PENDING' " +
        "and booking.schedulingDate = :date " +
        "and booking.schedulingTime <= :time")
    List<BookingEntity> listAllPendingSchedule(UUID productId, LocalDate date, LocalTime time);

}
