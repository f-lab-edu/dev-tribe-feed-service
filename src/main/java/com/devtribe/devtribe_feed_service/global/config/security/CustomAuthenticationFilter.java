package com.devtribe.devtribe_feed_service.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

/**
 * CustomAuthenticationFilter는 사용자의 인증 처리를 담당하는 필터입니다.
 * 이 필터는 사용자의 로그인 요청을 받아 인증 토큰을 생성하여 AuthenticationManager에게 인증을 요청하고
 * 인증된 객체를 HttpSession과 SecurityContext에 저장합니다.
 */
@Component
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationFilter(
        AuthenticationManager authenticationManager,
        CustomAuthenticationSuccess customAuthenticationSuccess,
        CustomAuthenticationFailure customAuthenticationFailure,
        ObjectMapper objectMapper
    ) {
        super(authenticationManager);
        super.setAuthenticationManager(authenticationManager);
        super.setFilterProcessesUrl("/api/v1/auth/login");
        super.setAuthenticationSuccessHandler(customAuthenticationSuccess);
        super.setAuthenticationFailureHandler(customAuthenticationFailure);
        super.setSecurityContextRepository(new HttpSessionSecurityContextRepository());

        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticationException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                "Authentication method not supported: " + request.getMethod());
        }

        LoginRequest loginRequest = parseRequestBody(request);

        validateLoginRequest(loginRequest);
        
        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
            .unauthenticated(loginRequest.email(), loginRequest.password());
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }

    private void validateLoginRequest(LoginRequest loginRequest) {
        if (loginRequest == null) {
            throw new AuthenticationServiceException("인증 요청이 올바르지 않습니다.");
        }

        if (loginRequest.email().trim().isEmpty()) {
            throw new AuthenticationServiceException("인증 요청이 올바르지 않습니다.");
        }

        if (loginRequest.password().trim().isEmpty()) {
            throw new AuthenticationServiceException("인증 요청이 올바르지 않습니다.");
        }
    }

    private LoginRequest parseRequestBody(HttpServletRequest request) {
        try {
            return objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }
}
