package ge.carapp.carappapi.schema;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.entity.UserRole;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserSchema(
    UUID id,
    String firstname,
    String lastname,
    String phone,
    UserRole userRole,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
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
