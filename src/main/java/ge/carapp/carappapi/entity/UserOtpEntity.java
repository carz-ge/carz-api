package ge.carapp.carappapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_OTP")
public class UserOtpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(mappedBy = "userOtp")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "OTP_STATUS", nullable = false)
    private OtpStatus otpStatus = OtpStatus.CREATED;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
    @Column(name = "EXPIRES_AT")
    private LocalDateTime expiresAt;
    @Column(name = "VERIFIED_AT")
    private LocalDateTime verifiedAt;

    @Column(name = "SEND_ATTEMPTS")
    private int sendAttempts = 0;

    @Column(name = "VERIFICATIOn_ATTEMPTS")
    private int verificationAttempts = 0;

    @Column(name = "OTP_HASH")
    private String otpHash;
}
