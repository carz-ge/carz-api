package ge.carapp.carappapi.entity.datacontainers;

import ge.carapp.carappapi.entity.UserEntity;

public record UserContainer(
    UserEntity userEntity,
    // indicates if user was created or not
    boolean created

) {


}
