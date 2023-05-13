package ge.carapp.carappapi.service;

import ge.carapp.carappapi.schema.graphql.UpdateUserInput;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.entity.UserRole;
import ge.carapp.carappapi.repository.UserRepository;
import ge.carapp.carappapi.schema.UserSchema;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;


    public final Optional<UserEntity> getUserByPhone(String phone) {
        return this.userRepository.findByPhone(phone);
    }

    public Optional<UserEntity> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    public final Optional<UserSchema> getUserSchemaByPhone(String phone) {
        return this.getUserByPhone(phone)
                .map(UserSchema::convert);
    }

    public final Optional<UserSchema> getUserSchemaById(UUID userId) {
        return this.getUserById(userId)
                .map(UserSchema::convert);
    }

    public UserSchema updateUser(UserEntity authenticatedUser, UpdateUserInput input) {
        boolean updated = false;
        if (!ObjectUtils.isEmpty(input.firstname())) {
            updated = true;
            authenticatedUser.setFirstname(input.firstname());
        }

        if (!ObjectUtils.isEmpty(input.lastname())) {
            updated = true;
            authenticatedUser.setLastname(input.lastname());
        }
        if (!updated) {
            // todo throw exception
        }

        return UserSchema.convert(authenticatedUser);
    }

    public Optional<UserEntity> getUserOrCreateByPhone(String phone) {
        Optional<UserEntity> user = userRepository
                .findByPhone(phone)
                .or(() -> {
                    final var creationTime = LocalDateTime.now();
                    UserEntity newUser = UserEntity.builder()
                            .phone(phone)
                            .userRole(UserRole.USER)
                            .createdAt(creationTime)
                            .updatedAt(creationTime)
                            .build();
                    return Optional.of(userRepository.save(newUser));
                });

        log.info("getting user {}", user);
        return user;
    }


}
