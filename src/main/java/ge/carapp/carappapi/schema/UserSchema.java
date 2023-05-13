package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.entity.UserRole;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserSchema {
    private final UUID id;
    private final String firstname;
    private final String lastname;
    private final String phone;
    private final UserRole userRole;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static UserSchema convert(UserEntity userEntity) {
        return UserSchema.builder()
                .id(userEntity.getId())
                .firstname(userEntity.getFirstname())
                .lastname(userEntity.getLastname())
                .phone(userEntity.getPhone())
                .userRole(userEntity.getUserRole())
                .createdAt(userEntity.getCreatedAt())
                .updatedAt(userEntity.getUpdatedAt())
                .build();
    }
}
