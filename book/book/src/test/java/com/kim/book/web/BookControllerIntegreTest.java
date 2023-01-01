package com.kim.book.web;

// 통합 테스트
// 모든 Bean들을 똑같이 IoC에 올리고 테스트

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kim.book.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private MockMvc mockMvc;

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
}
