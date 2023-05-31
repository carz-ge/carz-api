package ge.carapp.carappapi.security;

import ge.carapp.carappapi.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebFluxJwtAuthenticationFilter implements WebFilter {
    private static final String BEARER = "Bearer ";

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange,
                             WebFilterChain webFilterChain) {
        log.info("filter {}", serverWebExchange.getRequest());
        final List<String> authHeader = serverWebExchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || authHeader.isEmpty() || !authHeader.get(0).startsWith(BEARER)) {
            return webFilterChain.filter(serverWebExchange);
        }

        final String jwt = authHeader.get(0).substring(7);
        final String phone = jwtService.extractPhone(jwt);
        if (phone != null
            && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByPhone(phone);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                Authentication authToken = new PreAuthenticatedAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
                );

//                authToken.setDetails(new WebAuthenticationDetailsSource()
//                        .buildDetails(request));

//                SecurityContext context = SecurityContextHolder.createEmptyContext();
//                context.setAuthentication(authToken);
//                SecurityContextHolder.setContext(context);

                SecurityContextHolder
                    .getContext()
                    .setAuthentication(authToken);
            }

        }
        return webFilterChain.filter(serverWebExchange);
    }
}
