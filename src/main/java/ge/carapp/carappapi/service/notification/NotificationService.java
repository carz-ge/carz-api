package ge.carapp.carappapi.service.notification;


import ge.carapp.carappapi.models.firebase.CreatePushNotificationRequestModel;
import ge.carapp.carappapi.service.notification.push.FirebaseMessagingService;
import ge.carapp.carappapi.service.notification.sms.SmsNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
     private final SmsNotificationService smsNotificationService;
     private final FirebaseMessagingService pushNotificationService;

     public boolean sendSmsNotification(String phone, String message) {
         return smsNotificationService.sendSms(phone, message);
     }

    public void sendPushNotification(CreatePushNotificationRequestModel request) {
         pushNotificationService.sendPushNotification(request);
    }

}
