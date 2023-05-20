package ge.carapp.carappapi.config;

import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


@Configuration(proxyBeanMethods = false)
@EnableTransactionManagement
@EnableR2dbcRepositories("ge.carapp.carappapi.repository.r2dbc")
@EnableJpaRepositories("ge.carapp.carappapi.repository.jpa")
@RequiredArgsConstructor
@Import(DataSourceAutoConfiguration.class)
public class DatabaseConfiguration {
//    @Bean
//    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
//
//        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
//        initializer.setConnectionFactory(connectionFactory);
////        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
//
//        return initializer;
//    }
}
