package ge.carapp.carappapi.security.config;

import ge.carapp.carappapi.security.CustomAuthenticationManager;
import ge.carapp.carappapi.security.CustomUserDetailsService;
import ge.carapp.carappapi.security.WebFluxJwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
//@EnableMethodSecurity(
//        securedEnabled = true,
//        jsr250Enabled = true)
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class WebFluxSecurityConfiguration {
    private final CustomAuthenticationManager authenticationManager;
    private final WebFluxJwtAuthenticationFilter webFluxJwtAuthenticationFilter;

    @Bean
    SecurityWebFilterChain webHttpSecurity(ServerHttpSecurity http) {
        http
            .csrf().disable()
            .authorizeExchange(exchanges -> exchanges
                .anyExchange().permitAll()
            )

            .httpBasic()
            .authenticationManager(authenticationManager)
            .and()
            .addFilterBefore(webFluxJwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
        ;

        return http.build();
    }

}
