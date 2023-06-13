package ge.carapp.carappapi.service.notification.sms;


import lombok.extern.slf4j.Slf4j;

//@Service("defaultNotificationService")
@Slf4j
public class DefaultNotificationService implements SmsNotificationService {
    public boolean sendSms(String phone, String message) {
        log.info("Default implementation of NotificationService, sending to:{} ,{}", phone, message);
        return true;
    }
}

