package com.example.login.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

// SecurityContext에 유저 정보가 저장되는 시점을 다루는 클래스





public class SecurityUtil {
    private SecurityUtil() {}

    // Request가 들어오면
    // JwtFilter의 doFilter에서 저장
    // 거기에 있는 인증정보를 꺼내서,
    // 그 안의 id를 반환
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }
        return Long.parseLong(authentication.getName());
    }
}
