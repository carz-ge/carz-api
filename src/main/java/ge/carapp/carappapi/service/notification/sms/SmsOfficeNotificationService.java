package ge.carapp.carappapi.service.notification.sms;

import ge.carapp.carappapi.config.SmsConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service("smsoffice")
@Slf4j
@Profile("prod")
public class SmsOfficeNotificationService implements SmsNotificationService {

    private final SmsConfig smsConfig;
    private final WebClient client;

    public SmsOfficeNotificationService(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
        this.client = WebClient.create(smsConfig.getApiUrl());
    }

    public boolean sendSms(String phone, String message) {
        log.info("sending otp notification to {}, otp {}", phone, message);
        String query = "key=%s&destination=%s&sender=%s&content=%s"
            .formatted(
                smsConfig.getApiKey(),
                phone,
                smsConfig.getTitle(),
                message
            );
        try {
            // TODO initialize types
            Map response = this.client
                .get()
                .uri("/?" + query)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .retrieve()
                .bodyToFlux(Map.class)
                .blockLast();
            log.info("sms response {}, {}", response, response.getClass());
            return Boolean.TRUE.equals(response.get("Success"));
        } catch (Exception e) {
            log.error("error sending otp notification", e);
            return false;
        }
    }
}
