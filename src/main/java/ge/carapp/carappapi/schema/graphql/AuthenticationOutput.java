package ge.carapp.carappapi.schema.graphql;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationOutput {

    private String accessToken;
    private String refreshToken;
}
