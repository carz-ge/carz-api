package ge.carapp.carappapi.controller;

import ge.carapp.carappapi.entity.UserRole;
import ge.carapp.carappapi.exception.GeneralException;
import ge.carapp.carappapi.schema.graphql.AuthenticationInput;
import ge.carapp.carappapi.schema.graphql.AuthenticationOutput;
import ge.carapp.carappapi.schema.graphql.SendOptOutput;
import ge.carapp.carappapi.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Set;

import static ge.carapp.carappapi.service.ManagerService.managerPhoneId;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private static final Set<UserRole> USER_AND_ADMIN = Set.of(UserRole.USER, UserRole.ADMIN);
    private static final Set<UserRole> MANAGER_AND_ADMIN = Set.of(UserRole.MANAGER, UserRole.ADMIN);

    private final AuthService authService;

    @QueryMapping
    public Boolean checkPhone(@Argument String phone) {
        return authService.checkPhone(phone);
    }

    @MutationMapping
    public SendOptOutput sendOtp(@Argument String phone) {
        return authService.sendOtp(phone);
    }

    @MutationMapping
    @ExceptionHandler(GeneralException.class)
    public AuthenticationOutput authorize(@Argument AuthenticationInput input) {
        // Todo managers wouldn't be able to authenticate
        return authService.authorize(input.phone(), input.otp(), USER_AND_ADMIN);
    }

    @MutationMapping
    public SendOptOutput checkPhoneForManger(@Argument String phone) {
        return authService.checkPhoneForManger(managerPhoneId(phone), MANAGER_AND_ADMIN);
    }

    @MutationMapping
    @ExceptionHandler(GeneralException.class)
    public AuthenticationOutput authenticateManager(@Argument AuthenticationInput input) {
        return authService.authorize(managerPhoneId(input.phone()), input.otp(), MANAGER_AND_ADMIN);
    }

}
