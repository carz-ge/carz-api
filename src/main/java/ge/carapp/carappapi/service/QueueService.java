package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.ScheduleEntity;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.repository.jpa.ScheduleRepository;
import ge.carapp.carappapi.schema.ScheduledTimeSlotSchema;
import ge.carapp.carappapi.schema.graphql.ScheduleCarForServiceInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueService {
    private final ScheduleRepository scheduleRepository;


    public List<ScheduledTimeSlotSchema> listQueueByProviderId(UUID productId) {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        LocalTime upperQuarter = time.truncatedTo(ChronoUnit.HOURS)
            .plusMinutes( ((time.getMinute() / 15) + 1) * 15);

        log.info("date: {}, time: {}, UQ: {}", date,
            time,
            upperQuarter);


        List<ScheduleEntity> schedule = scheduleRepository.listAllPendingSchedule(
            productId,
            date,
            upperQuarter
            );

        log.info("Schedule list {}", schedule);

        // TODO need to be sorted by time and priority

        return schedule.stream()
            .map(ScheduledTimeSlotSchema::convert)
            .toList();
    }

    public Boolean scheduleCarByManager(UserEntity authenticatedUser, ScheduleCarForServiceInput input) {

        return true;
    }
}
