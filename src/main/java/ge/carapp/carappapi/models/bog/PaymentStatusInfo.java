package ge.carapp.carappapi.models.bog;

import com.fasterxml.jackson.annotation.JsonProperty;
import ge.carapp.carappapi.models.bog.details.OrderDetails;

public record PaymentStatusInfo(
    String event,
    @JsonProperty("request_time")
    String requestTime,
    OrderDetails body
) {
}
