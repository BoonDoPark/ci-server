package com.example.board.service;

import com.example.board.domain.entity.Board;
import com.example.board.dto.request.BoardRequest;
import com.example.board.dto.response.BoardResponse;

import java.util.List;

public interface BoardService {
    BoardResponse getBoardFromName(Long id);
    List<BoardResponse> getAllBoards();
    void createBoard(BoardRequest req);
    void deleteBoard(Long id);
}
