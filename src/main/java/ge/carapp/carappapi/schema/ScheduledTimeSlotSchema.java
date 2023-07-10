package ge.carapp.carappapi.schema;


import ge.carapp.carappapi.entity.BookingEntity;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record ScheduledTimeSlotSchema(
    String orderNumber,
    String carPlateNumber,
    LocalTime timeSlot
) {


    public static ScheduledTimeSlotSchema convert(BookingEntity scheduleEntity) {
        return ScheduledTimeSlotSchema.builder()
            .orderNumber(scheduleEntity.getOrderNumber())
            .carPlateNumber(scheduleEntity.getCarPlateNumber())
            .timeSlot(scheduleEntity.getSchedulingTime())
            .build();

    }
}
