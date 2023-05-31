package ge.carapp.carappapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class CarAppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarAppApiApplication.class, args);
    }

}
