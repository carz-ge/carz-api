package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.ProductEntity;
import ge.carapp.carappapi.entity.ScheduleEntity;
import ge.carapp.carappapi.repository.r2dbc.ScheduleReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class SchedulingService {
    private final ScheduleReactiveRepository scheduleReactiveRepository;


    public Flux<ScheduleEntity> getScheduleForProductReactive(
        ProductEntity product,
        LocalDate date,
        LocalTime time
    ) {
        return scheduleReactiveRepository.findAllByProductIdAndSchedulingDateAndSchedulingTime(product.getId(), date, time);
    }

}
