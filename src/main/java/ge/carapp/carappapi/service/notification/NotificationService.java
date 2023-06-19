package ge.carapp.carappapi.service.notification;


import ge.carapp.carappapi.models.firebase.CreatePushNotificationRequestModel;
import ge.carapp.carappapi.service.notification.push.ManagersFirebaseMessagingService;
import ge.carapp.carappapi.service.notification.push.UsersFirebaseMessagingService;
import ge.carapp.carappapi.service.notification.sms.SmsNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final SmsNotificationService smsNotificationService;
    private final UsersFirebaseMessagingService usersPushNotificationService;
    private final ManagersFirebaseMessagingService managersPushNotificationService;


    public boolean sendSmsNotification(String phone, String message) {
        log.info("send sms notification to phone :{}, message {}", phone, message);
        return smsNotificationService.sendSms(phone, message);
    }

    public void sendPushNotificationToUser(CreatePushNotificationRequestModel request) {
        log.info("send push notification to user: {}", request);
        usersPushNotificationService.sendPushNotification(request);
    }

    public void sendPushNotificationToManager(CreatePushNotificationRequestModel request) {
        log.info("send push notification to manager: {}", request);
        managersPushNotificationService.sendPushNotification(request);
    }
}
