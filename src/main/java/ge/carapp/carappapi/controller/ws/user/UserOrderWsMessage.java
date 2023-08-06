package ge.carapp.carappapi.controller.ws.user;

import java.util.UUID;

public record UserOrderWsMessage(
        UserOrderWsMessageType messageType,
        UUID orderId,
        boolean success,
        String message
) {


}
