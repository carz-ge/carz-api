package ge.carapp.carappapi.models.bog.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record Buyer(
    @JsonProperty("full_name")
    String fullName,
    @JsonProperty("masked_email")
    String maskedEmail,
    @JsonProperty("masked_phone")
    String maskedPhone
) {
}
