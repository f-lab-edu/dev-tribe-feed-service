package com.devtribe.devtribe_feed_service.global.config.security.authentication;

import com.devtribe.devtribe_feed_service.user.application.interfaces.UserRepository;
import com.devtribe.devtribe_feed_service.user.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("요청한 회원정보가 올바르지 않습니다."));

        return new CustomUserDetail(user);
    }
}
