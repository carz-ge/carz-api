package ge.carapp.carappapi.controller.rest;

import ge.carapp.carappapi.models.bog.PaymentStatusInfo;
import ge.carapp.carappapi.service.notification.NotificationService;
import ge.carapp.carappapi.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PaymentRestController {
    private final NotificationService notificationService;
    private final OrderService orderService;

    @PostMapping("/payment/{orderId}")
    ResponseEntity<Void> orderResultCallback(@PathVariable("orderId") String orderId,
                                             @RequestBody PaymentStatusInfo paymentInfo) {
        log.info("order result received: {}, requestBody: {}", orderId, paymentInfo);
        orderService.processPaymentCallbackResponse(orderId, paymentInfo);
        notificationService.sendNotificationToAdmin("Received payment callback, oid: %s, status: %s"
            .formatted(orderId, paymentInfo.body().orderStatus().key()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/payment/redirect/{orderId}/success")
    ResponseEntity<String> successRedirect(@PathVariable("orderId") String orderId, @RequestParam(value = "mock", defaultValue = "false") Boolean shouldMock) {
        if (Boolean.TRUE.equals(shouldMock)) {
           orderService.makeMockPaymentRequest(orderId, true);
        }
        log.info("payment success redirect: {}, mock: {}", orderId, shouldMock);
        return ResponseEntity.ok().body("payment success redirect: %s".formatted(orderId));
    }

    @GetMapping("/payment/redirect/{orderId}/reject")
    ResponseEntity<String> rejectRedirect(@PathVariable("orderId") String orderId, @RequestParam(value = "mock", defaultValue = "false") Boolean shouldMock) {
        if (Boolean.TRUE.equals(shouldMock)) {
           orderService.makeMockPaymentRequest(orderId, false);
        }
        log.info("payment fail redirect: {}, mock: {}", orderId, shouldMock);
        return ResponseEntity.ok().body("payment fail redirect: %s".formatted(orderId));
    }

}
