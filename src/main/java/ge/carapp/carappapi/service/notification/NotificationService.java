package ge.carapp.carappapi.service.notification;


import ge.carapp.carappapi.models.firebase.CreatePushNotificationRequestModel;
import ge.carapp.carappapi.service.notification.discord.DiscordService;
import ge.carapp.carappapi.service.notification.email.EmailService;
import ge.carapp.carappapi.service.notification.push.ManagersFirebaseMessagingService;
import ge.carapp.carappapi.service.notification.push.UsersFirebaseMessagingService;
import ge.carapp.carappapi.service.notification.sms.SmsNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final SmsNotificationService smsNotificationService;
    private final UsersFirebaseMessagingService usersPushNotificationService;
    private final ManagersFirebaseMessagingService managersPushNotificationService;
    private final DiscordService discordService;
    private final EmailService emailService;

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

    @Async
    public void sendNotificationToAdmin(String message) {
//        discordService.sendMessage(message)
//                .block();

        emailService.sendEmail("vanoganjelashvili@gmail.com", "Payment CallBack", message);
    }
}
