package ge.carapp.carappapi.service.notification.sms;


import ge.carapp.carappapi.service.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("defaultNotificationService")
@Slf4j
public class DefaultNotificationService implements NotificationService {
    public boolean sendNotification(String phone, String message) {
        log.info("Default implementation of NotificationService, sending to:{} ,{}", phone, message);
        return true;
    }
}

