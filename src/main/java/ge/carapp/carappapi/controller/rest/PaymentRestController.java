package ge.carapp.carappapi.controller.rest;

import ge.carapp.carappapi.models.bog.PaymentStatusInfo;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@Slf4j
public class PaymentRestController {

    @GetMapping("/payment/{orderId}")
    ResponseEntity<Void> orderResultCallback(@PathVariable("orderId") String orderId,
                                             @RequestBody PaymentStatusInfo paymentInfo) {
        log.info("order result received: {}, requestBody: {}", orderId, paymentInfo);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/payment/redirect/{orderId}/success")
    ResponseEntity<String> successRedirect(@PathVariable("orderId") String orderId) {
        log.info("payment success redirect: {}", orderId);
        return ResponseEntity.ok().body(orderId);
    }

    @GetMapping("/payment/redirect/{orderId}/reject")
    ResponseEntity<String> rejectRedirect(@PathVariable("orderId") String orderId) {
        log.info("payment fail redirect: {}", orderId);
        return ResponseEntity.ok().body(orderId);
    }

}
