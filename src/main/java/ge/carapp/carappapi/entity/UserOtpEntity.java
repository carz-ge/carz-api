package ge.carapp.carappapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_OTP")
public class UserOtpEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "OTP_STATUS", nullable = false)
    private OtpStatus otpStatus;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
    @Column(name = "EXPIRES_AT")
    private LocalDateTime expiresAt;
    @Column(name = "VERIFIED_AT")
    private LocalDateTime verifiedAt;

    @Column(name = "SEND_ATTEMPTS")
    private int sendAttempts;

    @Column(name = "VERIFICATIOn_ATTEMPTS")
    private int verificationAttempts;

    @Column(name = "OTP_HASH")
    private String otpHash;

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
