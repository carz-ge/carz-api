package ge.carapp.carappapi.service;

import ge.carapp.carappapi.core.Language;
import ge.carapp.carappapi.entity.UserDeviceEntity;
import ge.carapp.carappapi.entity.datacontainers.UserContainer;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.models.firebase.CreatePushNotificationRequestModel;
import ge.carapp.carappapi.schema.graphql.AddDeviceTokenInput;
import ge.carapp.carappapi.schema.graphql.UpdateUserInput;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.entity.UserRole;
import ge.carapp.carappapi.repository.UserRepository;
import ge.carapp.carappapi.schema.UserSchema;
import ge.carapp.carappapi.service.notification.NotificationService;
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
    private final UserDeviceRepository userDeviceRepository;
    private final NotificationService notificationService;


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

    public UserSchema updateUser(UserEntity user, UpdateUserInput input) {
        boolean shouldUpdate = false;
        if (!ObjectUtils.isEmpty(input.firstname())) {
            shouldUpdate = true;
            user.setFirstname(input.firstname());
        }

        if (!ObjectUtils.isEmpty(input.lastname())) {
            shouldUpdate = true;
            user.setLastname(input.lastname());
        }

        if (!ObjectUtils.isEmpty(input.email())) {
            shouldUpdate = true;
            user.setEmail(input.email());
        }

        if (!shouldUpdate) {
            // todo throw exception
            throw new GeneralException("empty values to update");
        }

        user = userRepository.save(user);

        return UserSchema.convert(user);
    }

    public UserContainer getUserOrCreateByPhone(String phone) {
        log.info("getting user by phone {}", phone);
        Optional<UserEntity> userEntityOptional = userRepository
            .findByPhone(phone);

        if (userEntityOptional.isPresent()) {
            return new UserContainer(userEntityOptional.get(), false);
        }

        final var creationTime = LocalDateTime.now();
        UserEntity newUser = UserEntity.builder()
            .phone(phone)
            .userRole(UserRole.USER)
            .language(Language.KA)
            .createdAt(creationTime)
            .updatedAt(creationTime)
            .build();
        newUser = userRepository.save(newUser);
        log.info("created new user for phone: {}", phone);
        return new UserContainer(newUser, true);
    }

    public void addDeviceToken(UserEntity user, AddDeviceTokenInput input) {
        log.info("add device token for user :{}, token {}", user.getId(), input.toString());
        UserDeviceEntity userDeviceEntity = UserDeviceEntity.builder()
            .deviceToken(input.deviceToken())
            .platform(input.platform())
            .user(user)
            .build();

        try {
            userDeviceRepository.save(userDeviceEntity);

        } catch (Exception e) {
            log.error("could not save device token {}", user.getId());
            e.printStackTrace();
            throw e;
        }

        // TODO
        notificationService.sendPushNotificationToUser(
            CreatePushNotificationRequestModel.builder()
                .token(input.deviceToken())
                .text("Super duper - სატესტო ტექსტი")
                .title("გამარჯობები სიხარულები")
                .build()

        );
    }

    public void removeUser(UserEntity user) {
        user.setRemoved(true);
        userRepository.save(user);
    }

    public void deactivateUser(UserEntity user) {
        user.setDeactivated(true);
        userRepository.save(user);
    }
}
