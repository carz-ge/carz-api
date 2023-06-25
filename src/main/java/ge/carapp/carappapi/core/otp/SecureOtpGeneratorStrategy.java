package ge.carapp.carappapi.core.otp;

import ge.carapp.carappapi.utils.Secrets;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component("SecureOtpGenerator")
@Profile("prod")
public class SecureOtpGeneratorStrategy implements OtpGenerationStrategy {
    @Override
    public String generate(int length) {
        return Secrets.generateNumericToken(length);
    }
}
