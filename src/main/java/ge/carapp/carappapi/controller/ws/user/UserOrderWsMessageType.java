package ge.carapp.carappapi.controller.ws.user;

import lombok.Getter;

@Getter
public enum UserOrderWsMessageType {
    MANAGER_RESPONSE("MANAGER_RESPONSE"),
    PAYMENT_RESPONSE("PAYMENT_RESPONSE");

    UserOrderWsMessageType(String value) {
        this.value = value;
    }

    private final String value;
}
