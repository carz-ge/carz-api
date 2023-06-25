package ge.carapp.carappapi.core.otp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class OtpGeneratorAdapter {
    private static final SimpleOtpStrategy SIMPLE_OTP_STRATEGY = new SimpleOtpStrategy();

    private final OtpGenerationStrategy otpGenerationStrategy;
    Set<String> devPhones = Set.of("+995551553907");

    public String generate(String phone, int tokenLength) {
        if (devPhones.contains(phone)) {
            SIMPLE_OTP_STRATEGY.generate();
        }
        return otpGenerationStrategy.generate(tokenLength);
    }

}
