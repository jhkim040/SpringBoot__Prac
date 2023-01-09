package com.example.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


// 토큰의 값을 헤더에서 뽑거나 헤더에서 삽입할때 쓰는 dto
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;

    private String email;
    private String nickname;

    public void setEmail(String email) {
        this.email = email;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
