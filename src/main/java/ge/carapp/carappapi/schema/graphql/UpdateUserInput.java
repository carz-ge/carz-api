package ge.carapp.carappapi.schema.graphql;

import org.springframework.lang.NonNull;

public record UpdateUserInput(@NonNull String firstname, @NonNull String lastname) {

}
