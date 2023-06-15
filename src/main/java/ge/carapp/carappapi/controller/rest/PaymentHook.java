package ge.carapp.carappapi.controller.rest;

import ge.carapp.carappapi.models.bog.PaymentStatusInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("payment/")
public class PaymentHook {

    @GetMapping("callback")
    PaymentStatusInfo callback() {

        return null;
    }
}
