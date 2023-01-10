package com.example.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// MemberController에 도달하기 전에
// Interceptor로 요청을 가로채고 header에 포함된 token decode
// 그 내용을 다시 요청으로 controller에 전달
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final BearerAuthInterceptor bearerAuthInterceptor;

    public WebMvcConfig(BearerAuthInterceptor bearerAuthInterceptor) {
        this.bearerAuthInterceptor = bearerAuthInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry){
        System.out.println(">>> register interceptor");
        // 어떤 요청을 가로챌지 등록
        // 애플리케이션 실행 --> 인터셉터를 등록하고 아래 주소로 들어오는 요청 기다림
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/member/me");
    }
}
