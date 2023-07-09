package ge.carapp.carappapi.entity;


import ge.carapp.carappapi.schema.CarType;
import ge.carapp.carappapi.schema.order.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "ORDERS")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ToString.Exclude
    @ManyToOne
//    @JoinColumn(foreignKey = @ForeignKey(name = "ORDER_TO_USER_FK"))
    private UserEntity user;

    private String idempotencyKey;
    private String redirectLink;
    private Integer totalPrice;
    private Integer commission;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private UUID productId;
    private UUID packageId;
    private UUID categoryId;
    private UUID providerId;
    private UUID managerId;

    private String schedulingDay;
    private String schedulingTime;
    @Enumerated(EnumType.STRING)
    private CarType carType;
    private String carPlateNumber;
    private String comment;

    private UUID bogOrderId;
    private String bogRedirectLink;
    private String errorMessage;

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
