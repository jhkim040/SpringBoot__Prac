package com.kim.book.service;

import com.kim.book.domain.Book;
import com.kim.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

// 기능을 정의, 트랜잭션을 관리 가능
// Repository에 여러 개의 함수 -> commit or rollback

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Transactional // 서비스 함수가 종료될 때 commit할지 rollback할지 트랜잭션 관리
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    // JPA 변경감지라는 내부 기능 활성화 X,
    // update 시의 정합성을 유지해줌
    // insert의 유령데이터(팬텀현상) 못막음
    @Transactional(readOnly = true)
    public Book findOne(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(()->
                        new IllegalArgumentException("id를 확인해주세요"));
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book update(Long id, Book book) {
        // dirty check update
        Book bookEntity = bookRepository.findById(id)
                .orElseThrow(()->
                new IllegalArgumentException("id를 확인해주세요")); // 영속화 (Book Object) --> 영속성 컨텍스트 보관
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        return bookEntity;
    } // 함수 종료 -> 트랜잭션 종료 -> 영속화 되어있는 데이터를 DB 갱신 (flush) --> commit(dirty check)

    @Transactional
    public String delete(Long id) {
        bookRepository.deleteById(id); // 오류가 터지면 이셉션
        return "ok";
    }
}
