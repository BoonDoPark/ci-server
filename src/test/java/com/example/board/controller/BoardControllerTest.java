package com.example.board.controller;

import com.example.board.dto.request.BoardRequest;
import com.example.board.dto.response.BoardResponse;
import com.example.board.service.BoardServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@ExtendWith(MockitoExtension.class)
@WebMvcTest(BoardController.class)
class BoardControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    BoardServiceImpl boardService;

    @BeforeEach
    void setUp() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new BoardController(boardService)).setControllerAdvice().build();
        this.mockMvc =  MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void getAllBoards() throws Exception {
        // given
        List<BoardResponse> boardResponses = new ArrayList<>();
        for(Long i = 0L; i<3; i++) {
            BoardResponse boardResponse = new BoardResponse(i, "asd", "askjfakha");
            boardResponses.add(boardResponse);
        }

        // when
        BDDMockito.given(boardService.getAllBoards()).willReturn(boardResponses);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/boards"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        Mockito.verify(boardService).getAllBoards();
    }

    @Test
    void getBoardFromName() throws Exception {
        // given
        BoardResponse boardResponse = new BoardResponse(1L, "asd", "aedfjnaksnfk");
        String id = "1";

        //when
        BDDMockito.given(boardService.getBoardFromName(1L)).willReturn(boardResponse);

        System.out.println("/" + id);
        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/boards/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("asd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("aedfjnaksnfk"))
                .andDo(MockMvcResultHandlers.print());
        Mockito.verify(boardService).getBoardFromName(1L);
    }

    @Test
    void createBoard() throws Exception {
        // given
        BoardRequest boardRequest = new BoardRequest("asd", "qwwjdhajsd");

        String content = new ObjectMapper().writeValueAsString(boardRequest);

        // when then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/boards")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        Mockito.verify(boardService).createBoard(new BoardRequest("asd", "qwwjdhajsd"));
    }

    @Test
    void deleteBoard() throws Exception {
        // given
        String id = "1";

        // when then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/boards/" + id))
                .andExpect(status().isOk())
                .andDo(print());

        Mockito.verify(boardService).deleteBoard(1L);
    }
}