package ge.carapp.carappapi.core.otp;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
//@Profile("dev")
public class SimpleOtpStrategy implements OtpGenerationStrategy {
    @Override
    public String generate(int length) {
        return generate();
    }

    public String generate() {
        return "123456";
    }
}
