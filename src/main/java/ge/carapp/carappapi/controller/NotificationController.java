package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.models.firebase.CreatePushNotificationRequestModel;
import ge.carapp.carappapi.schema.graphql.PushNotificationInput;
import ge.carapp.carappapi.service.notification.FirebaseMessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NotificationController {
    private final FirebaseMessagingService firebaseMessagingService;

    @MutationMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Boolean sendPushNotification(@Argument PushNotificationInput input) {
        firebaseMessagingService.sendPushNotification(
            CreatePushNotificationRequestModel.builder()
                .title(input.title())
                .token(input.deviceToken())
                .text(input.text())
                .build()
        );

        return true;
    }

}
