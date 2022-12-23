package com.oauthLogin.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴!!
// ORM -> Java(다른 언어) Object -> 테이블로 매핑
@Entity // Member Class --> MySQL에 테이블 생성
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다
    private int id;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    // DB는 RoleType이라는 타입이 없다.
    @Enumerated(EnumType.STRING)
    private RoleType role; // ex) ADMIN, USER
    @CreationTimestamp // 시간이 자동 입력
    private Timestamp createDate;
}
