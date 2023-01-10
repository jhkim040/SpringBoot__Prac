package com.example.login.service;

import com.example.login.entitiy.Member;
import com.example.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    //  email을 통해 실제로 존재하는지
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(email + "을 DB에서 찾을 수 없습니다"));
    }
    // 토큰에는 이메일, 암호, 권한으로...
    // Id값 사용하고 싶다면 리턴에 추가해주면 된다
    // 대신 그 전에 토큰들은 방식이 달라 못쓴다ㅠㅠ
    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().toString());

        return new User(
                String.valueOf(member.getEmail()),
                member.getPassword(),
                Collections.singleton(grantedAuthority)
        );
    }
}
