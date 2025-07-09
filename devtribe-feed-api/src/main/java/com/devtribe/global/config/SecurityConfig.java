package com.devtribe.global.config;

import com.devtribe.global.security.CustomAuthenticationFilter;
import com.devtribe.global.security.CustomUserDetailService;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
        CustomAuthenticationFilter authenticationFilter) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .logout(logout -> logout
                .logoutUrl("/api/v1/auth/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.sendRedirect("/api/v1/feeds");
                })
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers(
                    "/api/v1/users/signup",
                    "/api/v1/auth/login",
                    "/api/v1/auth/logout").permitAll()
                .requestMatchers("/api/v1/posts/search").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/posts").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/posts/*/vote").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/tags").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
                .requestMatchers("/api/v1/posts/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/v1/tags").authenticated()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(DaoAuthenticationProvider authenticationProvider) {
        return new ProviderManager(List.of(authenticationProvider));
    }

    /**
     * 실질적인 인증처리를 담당하는 Provider 객체입니다. userDetailService와 passwordEncoder을 통해
     * AuthenticationManager로부터 받은 인증 요청정보와 데이터베이스로부터 조회한 사용자의 정보를 비교하여 인증을 수행합니다.
     *
     * @param userDetailService
     * @param passwordEncoder
     * @return DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
        CustomUserDetailService userDetailService,
        PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailService);
        daoProvider.setPasswordEncoder(passwordEncoder);

        return daoProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
