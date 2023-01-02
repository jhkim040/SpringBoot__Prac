package com.kim.book.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


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

    @Test
    public void save_test() {
        // given
        Book book = new Book(null, "title1", "author1");
        // when
        Book bookEntity = bookRepository.save(book);
        // then
        assertEquals("title1", bookEntity.getTitle());
    }
}
