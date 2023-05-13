package ge.carapp.carappapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    public boolean sendOtpNotification(String phone, String otp) {
        log.info("sending otp notification to {}, otp {}", phone, otp);
        // TODO
        return true;
    }
}
