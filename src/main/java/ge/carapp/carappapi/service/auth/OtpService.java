package ge.carapp.carappapi.service.auth;

import ge.carapp.carappapi.core.DoubleTuple;
import ge.carapp.carappapi.core.otp.OtpGeneratorAdapter;
import ge.carapp.carappapi.entity.OtpStatus;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.entity.UserOtpEntity;
import ge.carapp.carappapi.repository.OtpRepository;
import ge.carapp.carappapi.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {
    private static final int OTP_LENGTH = 6;
    private static final int MAX_MISSED_OTP_SEND = 3;
    private static final int OTP_SEND_WINDOW_TIME_MINUTES = 15;
    private static final int OTP_EXPIRATION_TIME_MINUTES = 5;
    private static final int OTP_VERIFICATION_ATTEMPTS = 3;


    private static final List<OtpStatus> INVALID_OTP_VERIFICATION_STATUSES = List.of(
        OtpStatus.EXPIRED,
        OtpStatus.VERIFIED,
        OtpStatus.EXCEEDED_ATTEMPTS
    );

    private final OtpGeneratorAdapter otpGeneratorAdapter;
    private final OtpRepository otpRepository;
    private final NotificationService notificationService;

    public DoubleTuple<Boolean, LocalDateTime> sendOtpToUser(UserEntity user) {
        UserOtpEntity userOtp = user.getUserOtp();
        LocalDateTime timeNow = LocalDateTime.now();
        if (Objects.nonNull(userOtp)) {
            if (userOtp.getSendAttempts() >= MAX_MISSED_OTP_SEND) {
                if (userOtp.getCreatedAt()
                    .plus(OTP_SEND_WINDOW_TIME_MINUTES, ChronoUnit.MINUTES)
                    .isAfter(timeNow) && !userOtp.getOtpStatus().equals(OtpStatus.VERIFIED)) {
                    log.warn("User: {} exceeded max send attempts", user.getId());
                    return new DoubleTuple<>(false, userOtp.getExpiresAt());
                } else {
                    userOtp.setSendAttempts(0);
                    userOtp.setCreatedAt(timeNow);
                }
            }
        } else {
            userOtp = UserOtpEntity.builder()
                .user(user)
                .createdAt(timeNow)
                .sendAttempts(0)
                .build();
        }

        final String otp = generateOtpBasedOnUserPhone(user.getPhone());
        LocalDateTime expiresAt = timeNow.plus(OTP_EXPIRATION_TIME_MINUTES, ChronoUnit.MINUTES);
        userOtp.setOtpStatus(OtpStatus.CREATED);
        userOtp.setOtpHash(hashOtp(otp));
        userOtp.setUpdatedAt(timeNow);
        userOtp.setSendAttempts(userOtp.getSendAttempts() + 1);
        userOtp.setExpiresAt(expiresAt);
        userOtp.setVerificationAttempts(0);

        userOtp.setOtpStatus(OtpStatus.SENT);
        var notificationResult = notificationService.sendSmsNotification(user.getPhone(),
            "SMS Code: %s".formatted(otp)
        );
        if (!notificationResult) {
            log.error("OTP send to user: {} failed", user.getId());
            userOtp.setOtpStatus(OtpStatus.SEND_FAILED);
        }
        user.setUserOtp(userOtp);
        otpRepository.save(userOtp);
        return new DoubleTuple<>(notificationResult, expiresAt);

    }

    private String generateOtpBasedOnUserPhone(String phone) {
        return otpGeneratorAdapter.generate(phone, OTP_LENGTH);
    }


    public boolean verifyOtp(UserEntity user, String otp) {
        UserOtpEntity userOtp = user.getUserOtp();
        final var userId = user.getId();
        if (Objects.isNull(userOtp)) {
            log.warn("User: {} has no OTP", userId);
            return false;
        }

        if (INVALID_OTP_VERIFICATION_STATUSES.contains(userOtp.getOtpStatus())) {
            log.warn("User: {} OTP is invalid with saved status: {} ", userId, userOtp.getOtpStatus());
            return false;
        }

        if (userOtp.getExpiresAt().isBefore(LocalDateTime.now())) {
            log.warn("User: {} OTP is expired", userId);
            userOtp.setOtpStatus(OtpStatus.EXPIRED);
            otpRepository.save(userOtp);
            return false;
        }


        if (userOtp.getVerificationAttempts() >= OTP_VERIFICATION_ATTEMPTS) {
            log.warn("User: {} exceeded max verification attempts", userId);
            userOtp.setOtpStatus(OtpStatus.EXCEEDED_ATTEMPTS);
            otpRepository.save(userOtp);
            return false;
        }

        if (!userOtp.getOtpHash().equals(hashOtp(otp))) {
            log.warn("User: {} OTP is invalid", userId);
            userOtp.setOtpStatus(OtpStatus.MISSED);
            userOtp.setVerificationAttempts(userOtp.getVerificationAttempts() + 1);
            otpRepository.save(userOtp);
            return false;
        }


        userOtp.setOtpStatus(OtpStatus.VERIFIED);
        userOtp.setVerifiedAt(LocalDateTime.now());
        otpRepository.save(userOtp);
        return true;
    }


    private static String hashOtp(String otp) {
        return DigestUtils.sha256Hex(otp);
    }
}
