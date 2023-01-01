package com.kim.book.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;


// 단위 테스트
// DB 관련된 Bean이 IoC에 등록되면 됨

// AutoConfigureTestDatabase.Replace.ANY : 가짜 DB로 테스트
// AutoConfigureTestDatabase.Replace.NONE : 실제 DB로 테스트

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest // Reposiotory들을 다 IoC에 등록해둠.
public class BookRepositoryUnitTest {

    @Autowired
    private BookRepository bookRepository;
}
