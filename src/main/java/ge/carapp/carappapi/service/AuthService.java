package ge.carapp.carappapi.service;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.exception.NotAuthorizedException;
import ge.carapp.carappapi.jwt.JwtService;
import ge.carapp.carappapi.schema.UserSchema;
import ge.carapp.carappapi.schema.graphql.AuthenticationInput;
import ge.carapp.carappapi.schema.graphql.AuthenticationOutput;
import ge.carapp.carappapi.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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
    public Boolean sendOtp(String phone) {
        var userModel = userService.getUserOrCreateByPhone(phone);
        return userModel.filter(otpService::sendOtpToUser).isPresent();

    }

    public AuthenticationOutput authorize(AuthenticationInput input) throws NotAuthorizedException {
        UserEntity user = userService.getUserByPhone(input.getPhone())
                .orElseThrow(NotAuthorizedException::new);

        if (!otpService.verifyOtp(user, input.getOtp())) {
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
