package ge.carapp.carappapi.schema.graphql;

import org.springframework.lang.NonNull;

// TODO
public record UpdateUserInput(@NonNull String firstname, @NonNull String lastname, String email) {

}
