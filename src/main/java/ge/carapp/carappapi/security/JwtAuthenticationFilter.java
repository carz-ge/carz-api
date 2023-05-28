package ge.carapp.carappapi.security;

import ge.carapp.carappapi.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.debug("Request: {}", request);
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
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
        filterChain.doFilter(request, response);
    }
}
