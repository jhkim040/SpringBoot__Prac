package com.example.login.dto;

import com.example.login.entitiy.Authority;
import com.example.login.entitiy.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

// Request를 받을 때 쓰이는 dto
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    private String email;
    private String password;
    private String nickname;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .authority(Authority.ROLE_USER)
                .build();
    }

    // UsernamePasswordAuthenticationToken를 반환
    public UsernamePasswordAuthenticationToken toAuthentication() {
        // 아이디와 비밀번호가 일치하는지 검증하는 로직
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
