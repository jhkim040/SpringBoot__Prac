package com.kim.book.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // 서버 실행 시 Object Relation Mapping (테이블이 h2에 생성)
public class Book {
    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 해당 DB 번호 증가 전략 이용
    private Long id;

    private String title;
    private String author;
}
