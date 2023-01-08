package com.example.login.config;

import com.example.login.jwt.JwtAccessDeniedHandler;
import com.example.login.jwt.JwtAuthenticationEntryPoint;
import com.example.login.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    @Bean
    public PasswordEncoder passwordEncoder() {
        // equest로부터 받은 비밀번호를 암호화하기 위해 PasswordEncoder 빈을 생성
        return new BCryptPasswordEncoder();
    }


    // Spring Security는 2022년, 해당 기능을 deprecated
    // 대신 HttpSecurity를 Configuring해서 사용하라는 대안방식
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // https만을 사용하기위해 httpBasic을 disable
                // react에서 token을 localstorage에 저장할 것
                // csrf 방지또한 disable
                .csrf().disable()
                // REST API를 통해 세션 없이 토큰을 주고받으며 데이터를 주고받기
                // 세션설정또한 STATELESS로 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                // 이후 예외를 핸들링하는 것에서는 이전에 작성했던
                // JwtAuthenticationEntryPoint와 JwtAccessDeniedHandler
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                // 모든 Requests에 있어서 /auth/**를 제외한 모든 uri의 request는 토큰이 필요
                // /auth/**는 로그인 페이지
                .anyRequest().authenticated()

                .and()
                // 전에 설정한 JwtSecurityConfig클래스를 통해
                // tokenProvider를 적용
                .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }
}
