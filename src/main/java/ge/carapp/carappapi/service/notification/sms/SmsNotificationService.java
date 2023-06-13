package ge.carapp.carappapi.service.notification.sms;

public interface SmsNotificationService {
    boolean sendSms(String phone, String message);
}
