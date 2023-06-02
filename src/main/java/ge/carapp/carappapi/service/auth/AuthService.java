package ge.carapp.carappapi.service.auth;

import ge.carapp.carappapi.core.DoubleTuple;
import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.entity.datacontainers.UserContainer;
import ge.carapp.carappapi.exception.NotAuthorizedException;
import ge.carapp.carappapi.jwt.JwtService;
import ge.carapp.carappapi.schema.graphql.AuthenticationInput;
import ge.carapp.carappapi.schema.graphql.AuthenticationOutput;
import ge.carapp.carappapi.schema.graphql.SendOptOutput;
import ge.carapp.carappapi.security.CustomUserDetails;
import ge.carapp.carappapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
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

    public AuthenticationOutput authorize(AuthenticationInput input) throws NotAuthorizedException {
        UserEntity user = userService.getUserByPhone(input.phone())
                .orElseThrow(NotAuthorizedException::new);

        if (!otpService.verifyOtp(user, input.otp())) {
            throw new NotAuthorizedException();
        }

        UserDetails userDetails = new CustomUserDetails(user);

        Map<String, Object> extraClaims = Map.of("userId", user.getId().toString());

        var accessToken = jwtService.generateToken(extraClaims, userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);
        return AuthenticationOutput.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
