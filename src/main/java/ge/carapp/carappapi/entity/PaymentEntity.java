package ge.carapp.carappapi.entity;

import ge.carapp.carappapi.entity.json_converters.ListPaymentItemConverter;
import ge.carapp.carappapi.models.bog.details.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
@AllArgsConstructor
@Entity
@Table(name = "PAYMENT")
public class PaymentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID orderId;
    private UUID bogOrderId;

    private String industry;
    private String capture;

    // client
    private String clientId;
    private String brandKa;
    private String brandEn;
    private String clientUrl;


    private String bogCreateDate;
    private String bogExpireDate;
    private String bogOrderStatus;

    // PurchaseUnits
    private String requestAmount;
    private String transferAmount;
    private String refundAmount;
    private String currencyCode;

    @Convert(converter = ListPaymentItemConverter.class)
    private List<Item> items;

    // payment detail
    private String transferMethod;
    private String transactionId;
    private String payerIdentifier;
    private String paymentOption;
    private String cardType;
    private String cardExpirationDate;

    private String redirectLinkSuccess;
    private String redirectLinkFail;

    private String lang;
    private String rejectReason;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity user;

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
