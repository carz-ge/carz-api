package ge.carapp.carappapi.core.otp;


import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Component;

@Component
public interface OtpGenerationStrategy {
    String generate(@Min(0) int length);
}
