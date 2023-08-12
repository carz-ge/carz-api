package ge.carapp.carappapi.entity;

import ge.carapp.carappapi.schema.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
@Entity
@Table(name = "BOOKING")
public class BookingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @SequenceGenerator(name="SEQUENCE_ORDER_NUMBER", sequenceName="SEQUENCE_ORDER_NUMBER_SEQ", allocationSize = 50)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCE_ORDER_NUMBER")
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

    private String carPlateNumber;

    // Presented if this time slot was scheduled by the manager manually.
    private UUID managerId;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;


    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
