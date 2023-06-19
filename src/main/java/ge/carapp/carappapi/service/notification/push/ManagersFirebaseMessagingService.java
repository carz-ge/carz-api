package ge.carapp.carappapi.service.notification.push;

import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ManagersFirebaseMessagingService extends FirebaseMessagingService {
    public ManagersFirebaseMessagingService(
        @Qualifier("ManagersFirebaseMessagingApp") final FirebaseMessaging firebaseMessaging) {
        super(firebaseMessaging);
    }
}
