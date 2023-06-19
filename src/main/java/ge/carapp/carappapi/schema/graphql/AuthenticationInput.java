package ge.carapp.carappapi.schema.graphql;


import jakarta.validation.constraints.NotEmpty;

public record AuthenticationInput(@NotEmpty String phone, @NotEmpty String otp) {
}
