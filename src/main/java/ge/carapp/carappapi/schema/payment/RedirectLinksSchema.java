package ge.carapp.carappapi.schema.payment;

import ge.carapp.carappapi.models.bog.details.RedirectLinks;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RedirectLinksSchema(
    String success,
    String fail
) {
    public static RedirectLinksSchema from(@NotNull RedirectLinks redirectLinks) {
        return RedirectLinksSchema.builder()
            .fail(redirectLinks.fail())
            .success(redirectLinks.success())
            .build();
    }
}
