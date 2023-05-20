package ge.carapp.carappapi.repository;

import ge.carapp.carappapi.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, UUID> {


    Boolean findAllByProductIdAndSchedulingDateAndSchedulingTimeGreaterThan(UUID id,
                                                                            LocalDate date,
                                                                            LocalTime time,
                                                                            int capacity);
}
