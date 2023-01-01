package com.kim.book.web;

// 단위 테스트
// Controller 관련 로직만 띄우기
// Filter, ControllerAdvice


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.book.domain.Book;
import com.kim.book.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
}
