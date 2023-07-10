package ge.carapp.carappapi.config;

import ge.carapp.carappapi.controller.ws.ChatWebSocketHandler;
import ge.carapp.carappapi.controller.ws.manager.ManagersOrderWebSocketHandler;
import ge.carapp.carappapi.controller.ws.user.UserOrderWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@EnableWebSocketSecurity
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatWebSocketHandler chatWebSocketHandler;
    private final ManagersOrderWebSocketHandler managersOrderWebSocketHandler;
    private final UserOrderWebSocketHandler userOrderWebSocketHandler;

//    private final WebSocketAuthInterceptor interceptor;

    @Value("${car-app.origins}")
    private String[] allowedOrigins;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
            .addHandler(chatWebSocketHandler, "/chat")
            .addHandler(userOrderWebSocketHandler, "/user-order")
            .addHandler(managersOrderWebSocketHandler, "/manager-order")
            .setAllowedOrigins(allowedOrigins);
//            .addInterceptors(interceptor);
    }

    @Bean
    AuthorizationManager<Message<?>> authorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
        messages
            .simpDestMatchers("/chat")
            .hasAnyRole("ADMIN", "USER")
            .simpDestMatchers("/user-order")
            .hasAnyRole("ADMIN", "USER")
            .simpDestMatchers("/manager-order")
            .hasAnyRole("ADMIN", "MANAGER")

        ;
        return messages.build();
    }

}
