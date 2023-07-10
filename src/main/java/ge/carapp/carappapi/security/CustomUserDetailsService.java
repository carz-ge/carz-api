package ge.carapp.carappapi.security;

import ge.carapp.carappapi.entity.UserEntity;
import ge.carapp.carappapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByPhone(String phone) {
        UserEntity user = userService.getUserByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return new CustomUserDetails(user);
    }

    public UserDetails loadUserById(UUID userId) {
        UserEntity user = userService.getUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));

        return new CustomUserDetails(user);
    }
}
