package ge.carapp.carappapi.service.notification.push;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import ge.carapp.carappapi.models.firebase.CreatePushNotificationRequestModel;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Future;

@Slf4j
class FirebaseMessagingService {
    private final FirebaseMessaging firebaseMessaging;

    FirebaseMessagingService(@NotNull final FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    public void sendPushNotification(CreatePushNotificationRequestModel request) {
        Message message = Message.builder()
            .putData("foo", "bar")
            .setNotification(Notification.builder()
                .setTitle(request.title())
                .setBody(request.text())
                .build())
            .setToken(request.token())
            .build();

        try {
            String result = firebaseMessaging.send(message);
            log.info("Firebase result: {}", result);
        } catch (FirebaseMessagingException e) {
            log.error("Could not send message FirebaseMessagingException", e);
            throw new RuntimeException("Could not send message, firebase rejected it");
        } catch (Exception ex) {
            log.error("Could not send message Exception", ex);
            throw new RuntimeException("Could not send message");
        }
    }

    public Future<String> sendPushNotificationAsync(CreatePushNotificationRequestModel request) {
        Message message = Message.builder()
            .putData("foo", "bar async")
            .setNotification(Notification.builder()
                .setTitle(request.title())
                .setBody(request.text())
                .build())
            .setToken(request.token())
            .build();

        try {
            return firebaseMessaging.sendAsync(message);
        } catch (Exception ex) {
            log.error("Could not send message Exception", ex);
            throw new RuntimeException("Could not send message");
        }
    }

}
