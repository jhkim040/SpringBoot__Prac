package com.example.login.service;

import com.example.login.config.SecurityUtil;
import com.example.login.dto.MemberResponseDto;
import com.example.login.entitiy.Member;
import com.example.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional(readOnly = true)
    public MemberResponseDto findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id) {
        return memberRepository.findById(id)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }

    // (세션에 저장되어 있을 때 가능)
    // 헤더에 있는 token값을 토대로 Member의 data를 건내줌
    @Transactional
    public MemberResponseDto getMyInfoBySecurity() {
        return
                memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }

    // 닉네임 변경
    @Transactional
    public MemberResponseDto changeMemberNickname(String email, String nickname) {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        member.setNickname(nickname);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    // 비밀번호 변경
    @Transactional
    public MemberResponseDto changeMemberPassword(String email, String exPassword, String newPassword) {
//        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));

        if (!passwordEncoder.matches(exPassword, member.getPassword())) {
            throw new RuntimeException("비밀번호가 맞지 않습니다");
        }
        member.setPassword(passwordEncoder.encode((newPassword)));
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public String deleteByEmail(String email) {

        if(!memberRepository.existsByEmail(email)) {
//            throw new RuntimeException("존재하지 않은 사용자입니다.");
            return "fail";
        } else {
            Member member = memberRepository.findByEmail(email).get();
            memberRepository.delete(member);
            return "ok";
        }
    }
}
