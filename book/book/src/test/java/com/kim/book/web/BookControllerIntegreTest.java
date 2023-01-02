package com.kim.book.web;

// 통합 테스트
// 모든 Bean들을 똑같이 IoC에 올리고 테스트

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.book.domain.Book;
import com.kim.book.domain.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// WebEnvironment.RANDOM_POR : 실제 톰캣으로 테스트
// WebEnvironment.MOCK : 실제 톰캣을 올리는게 아니라 다른 톰켓으로 테스트
// @AutoConfigureMockMvc : MockMvc를 IoC에 등록
// @Transactional : 각각의 테스트함수가 종료될 때마다 트랜잭션을 rollback
@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BookControllerIntegreTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        entityManager
                .createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1")
                .executeUpdate();
    }

    @Test
    public void save_test() throws Exception {
        // given ( 테스트하기 위한 준비 )
        Book book = new Book(null, "spring", "course");
        String content = new ObjectMapper().writeValueAsString(book);


        // when ( 테스트 실행 )
        ResultActions resultAction = mockMvc.perform((post("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8)));

        // then ( 검증 )
        resultAction
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value( "spring"))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void findAll_test() throws Exception {
        // given
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "springboot", "course"));
        books.add(new Book(null, "reactjs", "course"));
        books.add(new Book(null, "JUnit", "kim"));
        bookRepository.saveAll(books);

        // when
        ResultActions resultAction = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[2].title").value("JUnit"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findById_test() throws Exception {
        // given
        Long id = 2L;

        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "springboot", "course"));
        books.add(new Book(null, "reactjs", "course"));
        books.add(new Book(null, "JUnit", "kim"));
        bookRepository.saveAll(books);

        // when
        ResultActions resultAction = mockMvc.perform(
                get("/book/{id}", id)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        // then
        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("reactjs"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void update_test() throws Exception {
        // given
        Long id = 3L;
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "springboot", "course"));
        books.add(new Book(null, "reactjs", "course"));
        books.add(new Book(null, "JUnit", "kim"));
        bookRepository.saveAll(books);

        Book book = new Book(null, "C++", "Arthur");
        String content = new ObjectMapper().writeValueAsString(book);

        // when
        ResultActions resultAction = mockMvc.perform(
                put("/book/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        // then
        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.title").value("C++"))
                .andExpect(jsonPath("$.author").value("Arthur"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void delete_test() throws Exception {
        // given
        Long id = 1L;
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "springboot", "course"));
        books.add(new Book(null, "reactjs", "course"));
        books.add(new Book(null, "JUnit", "kim"));
        bookRepository.saveAll(books);

        // when
        ResultActions resultAction = mockMvc.perform(
                delete("/book/{id}", id)
                        .accept(MediaType.TEXT_PLAIN));

        // then
        resultAction
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        // String return
        MvcResult requestResult = resultAction.andReturn();
        String result = requestResult.getResponse().getContentAsString();

        assertEquals("ok", result);
    }
}
