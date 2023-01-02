package com.kim.book.web;

// 단위 테스트
// Controller 관련 로직만 띄우기
// Filter, ControllerAdvice


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.book.domain.Book;
import com.kim.book.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


// Spring 환경으로 확장할 때 아래 어노테이션 필수
// JUnit 4 : @RunWith(SpringExtension.class)
// JUnit 5 : @ExtendWith({SpringExtension.class})
// @WebMvcTest에 포함되서 생략가능
@Slf4j
@WebMvcTest
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // IoC 환경에 bean 등록
    private BookService bookService;

    // BDDMockito Pattern : given, when/then
    @Test
    public void save_test() throws Exception {
        // given ( 테스트하기 위한 준비 )
        Book book = new Book(null, "spring", "course");
        String content = new ObjectMapper().writeValueAsString(book);
        when(bookService.save(book)).thenReturn(new Book(1L, "spring", "course"));

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
        books.add(new Book(1L, "springboot", "course"));
        books.add(new Book(2L, "reactjs", "course"));

        when(bookService.findAll()).thenReturn(books);

        // when
        ResultActions resultAction = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON_UTF8));

        //then
        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].title").value("springboot"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findById_test() throws Exception {
        // given
        Long id = 1L;
        when(bookService.findOne(id)).thenReturn(new Book(1L, "JAVA", "Arthur"));

        // when
        ResultActions resultAction = mockMvc.perform(
                get("/book/{id}", id)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
        );

        // then
        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("JAVA"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void update_test() throws Exception {
        // given
        Long id = 1L;
        Book book = new Book(null, "C++", "course");
        String content = new ObjectMapper().writeValueAsString(book);
        when(bookService.update(id, book)).thenReturn(new Book(1L, "C++", "Arthur"));

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
                .andExpect(jsonPath("$.author").value("Arthur"))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    public void delete_test() throws Exception {
        // given
        Long id = 1L;
        when(bookService.delete(id)).thenReturn("ok");

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

