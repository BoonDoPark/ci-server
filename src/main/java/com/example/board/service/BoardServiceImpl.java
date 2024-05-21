package com.example.board.service;

import com.example.board.domain.entity.Board;
import com.example.board.domain.repository.BoardRepository;
import com.example.board.dto.request.BoardRequest;
import com.example.board.dto.response.BoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public Board getBoardFromName(Long id) {
        return boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public List<Board> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        if (boards.isEmpty()) {
            throw new IllegalArgumentException("No boards found");
        }
        return boards;
    }

    @Override
    public void createBoard(BoardRequest req) {
        boardRepository.save(req.toEntity());
    }

    @Override
    public void deleteBoard() {
        boardRepository.deleteAll();
    }
}
