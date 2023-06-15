package ge.carapp.carappapi.models.bog;

import ge.carapp.carappapi.models.bog.details.OrderDetails;

public record PaymentStatusInfo(
    String event,
    String requestTime,
    OrderDetails body
) {
}
