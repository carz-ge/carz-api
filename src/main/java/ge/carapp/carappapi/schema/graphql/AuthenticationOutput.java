package ge.carapp.carappapi.schema.graphql;


import lombok.Builder;

@Builder
public record AuthenticationOutput(String accessToken, String refreshToken, Boolean shouldUpdateUserInfo) {
}
