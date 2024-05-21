package com.example.board.service;

import com.example.board.domain.entity.Board;
import com.example.board.domain.repository.BoardRepository;
import com.example.board.dto.request.BoardRequest;
import com.example.board.dto.response.BoardResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardServiceImpl boardService;

    @BeforeEach
    public void setUpTest() {
        boardService = new BoardServiceImpl(boardRepository);
    }

    @Test
    void getBoardFromName() {
        // given
        Board board = new Board(1L, "asd", "dasdfgeqaf");

        // when
        BDDMockito.when(boardRepository.findById(1L)).thenReturn(Optional.of(board));
        Board findBoard = boardService.getBoardFromName(1L);

        // then
        assertEquals(board.getName(), findBoard.getName());
        assertEquals(board.getText(), findBoard.getText());
        Mockito.verify(boardRepository).findById(1L);
    }

    @Test
    void getBoardFromNameExist() {
        BDDMockito.given(boardRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            boardService.getBoardFromName(1L);
        });

        Mockito.verify(boardRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void getAllBoards() {
        // given
        List<Board> boards = new ArrayList<>();
        for(int i=0; i<3; i++) {
            Board board = new Board(1L, "asd", "dasdfgeqaf");
            boards.add(board);
        }

        // when
        BDDMockito.when(boardRepository.findAll()).thenReturn(boards);
        List<Board> boardList = boardService.getAllBoards();

        // then
        assertEquals(boards.get(0).getName(), boardList.get(0).getName());
        assertEquals(boards.get(0).getText(), boardList.get(0).getText());
        assertEquals(boards.get(1).getName(), boardList.get(1).getName());
        assertEquals(boards.get(1).getText(), boardList.get(1).getText());
        assertEquals(boards.get(2).getName(), boardList.get(2).getName());
        assertEquals(boards.get(2).getText(), boardList.get(2).getText());
        Mockito.verify(boardRepository).findAll();
    }

    @Test
    void getAllBoardExist() {
        BDDMockito.given(boardRepository.findAll()).willReturn(Collections.emptyList());

         assertThrows(IllegalArgumentException.class, () -> {
            boardService.getAllBoards();
        });

        Mockito.verify(boardRepository, Mockito.times(1)).findAll();
    }

    @Test
    void createBoard() {

    }

    @Test
    void deleteBoard() {
    }
}