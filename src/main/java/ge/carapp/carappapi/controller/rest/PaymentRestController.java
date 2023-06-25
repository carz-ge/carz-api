package ge.carapp.carappapi.controller.rest;

import ge.carapp.carappapi.models.bog.PaymentStatusInfo;
import ge.carapp.carappapi.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PaymentRestController {
    private final NotificationService notificationService;

    @PostMapping("/payment/{orderId}")
    ResponseEntity<Void> orderResultCallback(@PathVariable("orderId") String orderId,
                                             @RequestBody PaymentStatusInfo paymentInfo) {
        log.info("order result received: {}, requestBody: {}", orderId, paymentInfo);

        notificationService.sendNotificationToAdmin("Received payment callback, oid: %s, status: %s"
            .formatted(orderId, paymentInfo.body().orderStatus().key()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/payment/redirect/{orderId}/success")
    ResponseEntity<String> successRedirect(@PathVariable("orderId") String orderId) {
        log.info("payment success redirect: {}", orderId);
        return ResponseEntity.ok().body("payment success redirect: %s".formatted(orderId));
    }

    @GetMapping("/payment/redirect/{orderId}/reject")
    ResponseEntity<String> rejectRedirect(@PathVariable("orderId") String orderId) {
        log.info("payment fail redirect: {}", orderId);
        return ResponseEntity.ok().body("payment fail redirect: %s".formatted(orderId));
    }

}
