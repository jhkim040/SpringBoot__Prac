package com.example.login.config;

import com.example.login.jwt.TokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class BearerAuthInterceptor implements HandlerInterceptor {
    private AuthorizationExtractor authExtractor;
    private TokenProvider tokenProvider;
    public BearerAuthInterceptor(AuthorizationExtractor authExtractor, TokenProvider tokenProvider) {
        this.authExtractor = authExtractor;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
//        System.out.println(">>> interceptor.preHandle ");

        // request로부터 token 추출
        String token = authExtractor.extract(request, "Bearer");
        if (StringUtils.isEmpty(token)) {
            return true;
        }

        if (!tokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("not validate token");
        }

        // decode token --> request에 setting
        String email = tokenProvider.getSubject(token);
        request.setAttribute("email", email);
        return true;
    }
}
