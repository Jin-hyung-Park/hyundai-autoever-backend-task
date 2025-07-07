package com.autoever.backend;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 보안 설정 클래스입니다.
 * - H2 콘솔, 관리자 API, 사용자 API 접근 권한 및 인증 방식 설정
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * 비밀번호 암호화에 사용할 PasswordEncoder 빈 등록
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * HTTP 보안 필터 체인 설정
     * - H2 콘솔 허용, 관리자 API 인증, 나머지 모두 허용
     * - CSRF/FrameOptions 비활성화
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()); // CSRF 비활성화
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // H2 콘솔용 FrameOptions 비활성화
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll() // H2 콘솔 허용
                .requestMatchers("/api/admin/**").authenticated() // 관리자 API 인증 필요
                .anyRequest().permitAll() // 그 외 모두 허용
        );
        http.httpBasic(Customizer.withDefaults()); // Basic 인증 사용
        return http.build();
    }

    /**
     * 관리자 계정(admin/1212) InMemory 등록
     */
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder encoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername("admin")
                        .password(encoder.encode("1212"))
                        .roles("ADMIN")
                        .build()
        );
    }
}
