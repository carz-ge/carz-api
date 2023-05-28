package ge.carapp.carappapi.entity;

import ge.carapp.carappapi.schema.TimeSlotStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "SCHEDULE")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String orderNumber;

    @Column(nullable = false)
    private UUID categoryId;
    @Column(nullable = false)
    private UUID productId;
    @Column(nullable = false)
    private UUID productDetailsId;
    @Column(nullable = false)
    private UUID providerId;


    @Column(nullable = false)
    private LocalDate schedulingDate;
    @Column(nullable = false)
    private LocalTime schedulingTime;

    private UUID userId;
    private UUID carId;

    private String carPlateNumber;
    private String phoneNumber;

    // Presented if this time slot was scheduled by the manager manually.
    private UUID managerId;

    @Enumerated(EnumType.STRING)
    private TimeSlotStatus status;
}
