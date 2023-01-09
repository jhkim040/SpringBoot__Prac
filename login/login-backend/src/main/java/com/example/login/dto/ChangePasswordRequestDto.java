package com.example.login.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 비밀번호를 수정할 때 쓰이는 dto
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDto {
    private String email;
    private String exPassword;
    private String newPassword;
}
