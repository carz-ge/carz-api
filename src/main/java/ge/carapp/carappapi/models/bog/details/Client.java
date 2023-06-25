package ge.carapp.carappapi.models.bog.details;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Client(
    String id,
    @JsonProperty("brand_ka")
    String brandKa,
    @JsonProperty("brand_en")
    String brandEn,
    String url
) {
}
