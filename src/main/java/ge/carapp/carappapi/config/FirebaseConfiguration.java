package ge.carapp.carappapi.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Data
@Configuration
@ConfigurationProperties(prefix = "firebase")
public class FirebaseConfiguration {
    private String usersAppConfig;
    private String managersAppConfig;


    @Bean("UsersFirebaseMessagingApp")
    public FirebaseMessaging firebaseMessaging() throws IOException {
        FirebaseApp app = initFirebaseApp(usersAppConfig, "users-app");
        return FirebaseMessaging.getInstance(app);
    }

    @Bean("ManagersFirebaseMessagingApp")
    public FirebaseMessaging managersFirebaseMessaging() throws IOException {
        FirebaseApp app = initFirebaseApp(managersAppConfig, "managers-app");
        return FirebaseMessaging.getInstance(app);
    }


    @NotNull
    private static FirebaseApp initFirebaseApp(String path, String name) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream
            (new ClassPathResource(path).getInputStream());
        FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(credentials)
            .setConnectTimeout(5000)
            .setReadTimeout(5000)
            .build();
        return FirebaseApp.initializeApp(options, name);
    }

}
