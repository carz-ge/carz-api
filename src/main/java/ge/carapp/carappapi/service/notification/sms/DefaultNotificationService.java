package ge.carapp.carappapi.service.notification.sms;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service("defaultNotificationService")
@Slf4j
//@Profile("dev")
public class DefaultNotificationService implements SmsNotificationService {
    public boolean sendSms(String phone, String message) {
        log.info("Default implementation of NotificationService, sending to:{} ,{}", phone, message);
        return true;
    }
}

