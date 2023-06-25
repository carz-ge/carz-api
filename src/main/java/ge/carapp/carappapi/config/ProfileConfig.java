package ge.carapp.carappapi.config;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProfileConfig {


    private final Environment environment;


    @PostConstruct
    void postConstruct() {
        String[] activeProfiles = environment.getActiveProfiles();
        log.info("active profiles: {}", Arrays.toString(activeProfiles));
    }

}
