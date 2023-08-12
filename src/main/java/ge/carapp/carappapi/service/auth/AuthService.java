package ge.carapp.carappapi.service.auth;

import ge.carapp.carappapi.core.DoubleTuple;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.entity.UserRole;
import ge.carapp.carappapi.entity.datacontainers.UserContainer;
import ge.carapp.carappapi.exception.NotAuthorizedException;
import ge.carapp.carappapi.jwt.JwtService;
import ge.carapp.carappapi.schema.graphql.AuthenticationOutput;
import ge.carapp.carappapi.schema.graphql.SendOptOutput;
import ge.carapp.carappapi.security.CustomUserDetails;
import ge.carapp.carappapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserService userService;
    private final OtpService otpService;
    private final JwtService jwtService;

    public Boolean checkPhone(String phone) {
        return userService.getUserSchemaByPhone(phone).isPresent();
    }

    //    @Transactional
    public SendOptOutput sendOtp(String phone) {
        UserContainer userContainer = userService.getUserOrCreateByPhone(phone);

        DoubleTuple<Boolean, LocalDateTime> sendOtpServiceResult = otpService.sendOtpToUser(userContainer.userEntity());
        return SendOptOutput.builder()
            .isRegistered(!userContainer.created())
            .sent(sendOtpServiceResult.first())
            .expiresAt(sendOtpServiceResult.second())
            .build();
    }

    public AuthenticationOutput authorize(String phone, String otp, Set<UserRole> roles) throws NotAuthorizedException {
        UserEntity user = userService.getUserByPhone(phone)
            .orElseThrow(NotAuthorizedException::new);

        if (!roles.contains(user.getUserRole()) || !otpService.verifyOtp(user, otp)) {
            throw new NotAuthorizedException();
        }

        return getAuthenticationToken(user);
    }

    private AuthenticationOutput getAuthenticationToken(UserEntity user) {
        UserDetails userDetails = new CustomUserDetails(user);

        Map<String, Object> extraClaims = Map.of("userId", user.getId().toString());

        var accessToken = jwtService.generateToken(extraClaims, userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);
        return AuthenticationOutput.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .shouldUpdateUserInfo(user.getShouldUpdateInfo())
            .build();
    }

    public SendOptOutput checkPhoneForManger(String phone, Set<UserRole> managerAndAdmin) {
        Optional<UserEntity> user = userService.getUserByPhone(phone);
        if (user.isEmpty() || !managerAndAdmin.contains(user.get().getUserRole()) || Boolean.TRUE.equals(user.get().getRemoved())) {
            log.warn("Manager not found: {}", phone);
            return SendOptOutput.builder().sent(false).build();
        }

        DoubleTuple<Boolean, LocalDateTime> sendOtpServiceResult = otpService.sendOtpToUser(user.get());
        return SendOptOutput.builder()
            .sent(sendOtpServiceResult.first())
            .expiresAt(sendOtpServiceResult.second())
            .build();
    }
}
