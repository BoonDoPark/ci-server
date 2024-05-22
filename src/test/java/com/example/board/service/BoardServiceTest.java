package com.example.board.service;

import com.example.board.domain.entity.Board;
import com.example.board.domain.repository.BoardRepository;
import com.example.board.dto.request.BoardRequest;
import com.example.board.dto.response.BoardResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        BoardResponse findBoard = boardService.getBoardFromName(1L);

        // then
        assertEquals(board.getId(), findBoard.id());
        assertEquals(board.getName(), findBoard.name());
        assertEquals(board.getText(), findBoard.text());
        Mockito.verify(boardRepository).findById(1L);
    }

    @Test
    void getBoardFromNameExist() {
        BDDMockito.given(boardRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            boardService.getBoardFromName(1L);
        });

        Mockito.verify(boardRepository, times(1)).findById(1L);
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
        List<BoardResponse> boardList = boardService.getAllBoards();

        // then
        assertEquals(boards.get(0).getName(), boardList.get(0).name());
        assertEquals(boards.get(0).getText(), boardList.get(0).text());
        assertEquals(boards.get(1).getName(), boardList.get(1).name());
        assertEquals(boards.get(1).getText(), boardList.get(1).text());
        assertEquals(boards.get(2).getName(), boardList.get(2).name());
        assertEquals(boards.get(2).getText(), boardList.get(2).text());
        Mockito.verify(boardRepository).findAll();
    }

    @Test
    void getAllBoardExist() {
        BDDMockito.given(boardRepository.findAll()).willReturn(Collections.emptyList());

         assertThrows(IllegalArgumentException.class, () -> {
            boardService.getAllBoards();
        });

        Mockito.verify(boardRepository, times(1)).findAll();
    }

    @Test
    void createBoard() {
        BoardRequest boardRequest = new BoardRequest("asd", "dasdfgeqaf");
        Mockito.when(boardRepository.save(any(Board.class))).then(AdditionalAnswers.returnsFirstArg());

        boardService.createBoard(boardRequest);

        Assertions.assertEquals(boardRequest.name(), "asd");
        Assertions.assertEquals(boardRequest.text(), "dasdfgeqaf");

        Mockito.verify(boardRepository).save(any());
    }

    @Test
    void createBoardExist() {

    }

    @Test
    void deleteBoard() {
        // given
        Long id = 1L;
        BDDMockito.given(boardRepository.findById(id)).willReturn(Optional.of(new Board(1L, "asd", "wkkasdhjasd")));
        Mockito.doNothing().when(boardRepository).deleteById(id);

        // when
        boardService.deleteBoard(id);

        // then
        verify(boardRepository).findById(id);
        verify(boardRepository).deleteById(id);
    }

    @Test
    void deleteBoardExist() {
        Long id = 500L;

        BDDMockito.given(boardRepository.findById(id)).willReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            boardService.deleteBoard(id);
        });
        verify(boardRepository).findById(id);
    }
}