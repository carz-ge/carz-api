package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.models.firebase.CreatePushNotificationRequestModel;
import ge.carapp.carappapi.schema.graphql.PushNotificationInput;
import ge.carapp.carappapi.schema.graphql.SmsNotificationInput;
import ge.carapp.carappapi.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean sendPushNotification(@Argument PushNotificationInput input) {
        notificationService.sendPushNotification(
            CreatePushNotificationRequestModel.builder()
                .title(input.title())
                .token(input.deviceToken())
                .text(input.text())
                .build()
        );

        return true;
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean sendSmsNotification(@Argument SmsNotificationInput input) {
        return notificationService.sendSmsNotification(input.phone(), input.text());
    }
}
