package com.example.board.service;

import com.example.board.domain.entity.Board;
import com.example.board.dto.request.BoardRequest;

import java.util.List;

public interface BoardService {
    Board getBoardFromName(Long id);
    List<Board> getAllBoards();
    void createBoard(BoardRequest req);
    void deleteBoard();
}
