package com.devtribe.devtribe_feed_service.global.config.security;


import com.devtribe.devtribe_feed_service.user.domain.User;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetail implements UserDetails, CredentialsContainer {

    private final User user;

    public CustomUserDetail(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        /// TODO : 권한 정보 추가 예정. 현재는 빈 값으로 반환
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public void eraseCredentials() {
        user.changePassword(null);
    }
}
