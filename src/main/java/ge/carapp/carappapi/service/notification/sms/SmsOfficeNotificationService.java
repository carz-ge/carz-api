package ge.carapp.carappapi.service.notification.sms;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import ge.carapp.carappapi.service.notification.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

//@Service("smsoffice")
@Slf4j
public class SmsOfficeNotificationService implements NotificationService {

    private final WebClient client;

    private final ObjectMapper objectMapper;

    // https://smsoffice.ge/api/v2/send?
    public SmsOfficeNotificationService() {
        client = WebClient.create("https://smsoffice.ge/api/v2/send");
        objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    //6f5fc7ba49344723b22e3e52e1aea302
    public boolean sendNotification(String phone, String message) {
        log.info("sending otp notification to {}, otp {}", phone, message);
        // TODO
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("destination", phone);
//        map.put("content", "your code is " + otp);
//        map.put("no_sms", false);
//        map.put("key", "63ef00511c28af0caf599c7eb71cda55f50afd62a1724fb51c82282ff12e3ece");
//        map.put("brand_name", SENDER);
        String query = "key=%s&destination=%s&sender=%s&content=%s"
            .formatted(
                "6f5fc7ba49344723b22e3e52e1aea302",
                phone,
                "carz",
                message
            );
        try {
//            String json = objectMapper.writeValueAsString(map);
            log.info("json {}", query);

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
