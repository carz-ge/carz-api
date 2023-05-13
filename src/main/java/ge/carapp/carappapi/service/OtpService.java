package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.OtpStatus;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.entity.UserOtpEntity;
import ge.carapp.carappapi.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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

    private static final SecureRandom random = new SecureRandom();

    private static final List<OtpStatus> INVALID_OTP_VERIFICATION_STATUSES = List.of(
            OtpStatus.EXPIRED,
            OtpStatus.VERIFIED,
            OtpStatus.EXCEEDED_ATTEMPTS
    );


    private final OtpRepository otpRepository;
    private final NotificationService notificationService;

    public boolean sendOtpToUser(UserEntity user) {
        UserOtpEntity userOtp = user.getUserOtp();
        LocalDateTime timeNow = LocalDateTime.now();
        if (Objects.nonNull(userOtp)) {
            if (userOtp.getOtpStatus().equals(OtpStatus.SENT)) {
                log.warn("OTP was already sent to user: {}", user.getId());
                return false;
            }
            if (userOtp.getSendAttempts() >= MAX_MISSED_OTP_SEND &&
                    userOtp.getCreatedAt()
                            .plus(OTP_SEND_WINDOW_TIME_MINUTES, ChronoUnit.MINUTES)
                            .isBefore(timeNow)) {
                log.warn("User: {} exceeded max send attempts", user.getId());
                return false;
            }
        } else {
            userOtp = UserOtpEntity.builder()
                    .user(user)
                    .createdAt(timeNow)
                    .build();
        }

        final String otp = generateOtp();
        userOtp.setOtpStatus(OtpStatus.CREATED);
        userOtp.setOtpHash(hashOtp(otp));
        userOtp.setUpdatedAt(timeNow);
        userOtp.setSendAttempts(userOtp.getSendAttempts() + 1);
        userOtp.setExpiresAt(timeNow.plus(OTP_EXPIRATION_TIME_MINUTES, ChronoUnit.MINUTES));
        userOtp.setVerificationAttempts(0);

        var notificationResult = notificationService.sendOtpNotification(user.getPhone(), otp);
        if (!notificationResult) {
            log.error("OTP send to user: {} failed", user.getId());
            return false;
        }
        userOtp.setOtpStatus(OtpStatus.SENT);
        user.setUserOtp(userOtp);
        otpRepository.save(userOtp);

        return true;
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

    private static String generateOtp() {
        StringBuilder sb = new StringBuilder(OTP_LENGTH);

        for (int i = 0; i < OTP_LENGTH; i++) {
            int randomInt = random.nextInt(10);
            sb.append(randomInt);
        }

        return sb.toString();
    }

    private static String hashOtp(String otp) {
        return DigestUtils.sha256Hex(otp);
    }
}
