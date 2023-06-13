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

@Controller
@RequiredArgsConstructor
public class AuthController {
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
        return authService.authorize(input, UserRole.USER); // todooo managers wouldn't be able to authenticate
    }

    @MutationMapping
    public SendOptOutput checkPhoneForManger(@Argument String phone) {
        return authService.checkPhoneForManger(phone);
    }

    @MutationMapping
    @ExceptionHandler(GeneralException.class)
    public AuthenticationOutput authenticateManager(@Argument AuthenticationInput input) {
        return authService.authorize(input, UserRole.MANAGER);
    }


}
