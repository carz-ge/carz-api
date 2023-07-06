package ge.carapp.carappapi.entity;


import ge.carapp.carappapi.schema.CarType;
import ge.carapp.carappapi.schema.order.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ORDER")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(foreignKey=@ForeignKey(name="ORDER_TO_USER_FK"))
    private UserEntity user;

    String idempotencyKey;
    String redirectLink;
    Integer totalPrice;
    @Enumerated(EnumType.STRING)
    OrderStatus status;

    UUID productId;
    UUID packageId;
    UUID categoryId;
    UUID providerId;
    UUID managerId;

    String schedulingDay;
    String schedulingTime;
    @Enumerated(EnumType.STRING)
    CarType carType;
    String carPlateNumber;
    String comment;

    UUID bogOrderId;
    String bogRedirectLink;

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
