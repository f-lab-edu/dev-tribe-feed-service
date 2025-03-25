package com.devtribe.devtribe_feed_service.global.config.jpa;

import com.devtribe.devtribe_feed_service.global.config.security.authentication.CustomUserDetail;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContextHolderStrategy().getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .filter(principal -> principal instanceof CustomUserDetail)
            .map(principal -> ((CustomUserDetail) principal).getUser().getId());
    }
}
