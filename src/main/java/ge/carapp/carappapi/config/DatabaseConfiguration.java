package ge.carapp.carappapi.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("ge.carapp.carappapi")
//@EnableTransactionManagement
public class DatabaseConfiguration  {

//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//        return
//    }
}
