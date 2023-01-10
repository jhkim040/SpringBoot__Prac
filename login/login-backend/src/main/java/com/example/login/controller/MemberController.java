package com.example.login.controller;

import com.example.login.dto.ChangePasswordRequestDto;
import com.example.login.dto.MemberRequestDto;
import com.example.login.dto.MemberResponseDto;
import com.example.login.dto.TokenDto;
import com.example.login.entitiy.Member;
import com.example.login.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

// 세션에 저장할 때 사용
//    @GetMapping("/me")
//    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
//        MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
//        System.out.println(myInfoBySecurity.getNickname());
//        return ResponseEntity.ok((myInfoBySecurity));
//    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo(HttpServletRequest request) {
        // Interceptor로 가로챈 Header의 Authorization 내의 token 정보 추출 후
        // request에 담은 사용자 정보(id)
        Object emailTemp = request.getAttribute("email");

        if(emailTemp == null) {
            throw new RuntimeException("non-existing user");
        }
        String email = (String) emailTemp;
        MemberResponseDto myMemberInfo = memberService.findByEmail(email);

        return ResponseEntity.ok(myMemberInfo);
    }


    @PostMapping("/nickname")
    public ResponseEntity<MemberResponseDto> setMemberNickname(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberNickname(request.getEmail(), request.getNickname()));
    }


    @PostMapping("/password")
    public ResponseEntity<MemberResponseDto> setMemberPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getEmail(), request.getExPassword(), request.getNewPassword()));
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<?> deleteByEmail(@PathVariable String email) {
        log.info(email);
        return new ResponseEntity<>(memberService.deleteByEmail(email), HttpStatus.OK);
    }
}
