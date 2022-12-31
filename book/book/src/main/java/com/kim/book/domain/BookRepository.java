package com.kim.book.domain;

import com.kim.book.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository 적어야 spring IoC에 빈으로 등록
// 하지만 JpaRepository를 extends하면 생략 가능
// JpaRepository는 CRUD 함수를 가지고 있음
public interface BookRepository extends JpaRepository<Book, Long> {
}
