package com.example.board.service;

import com.example.board.domain.entity.Board;
import com.example.board.domain.repository.BoardRepository;
import com.example.board.dto.request.BoardRequest;
import com.example.board.dto.response.BoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardRepository boardRepository;

    @Override
    public BoardResponse getBoardFromName(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return BoardResponse.from(board);
    }

    @Override
    public List<BoardResponse> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        if (boards.isEmpty()) {
            throw new IllegalArgumentException("No boards found");
        }
        return boards.stream().map(BoardResponse::from).toList();
    }

    @Override
    public void createBoard(BoardRequest req) {
        boardRepository.save(req.toEntity());
    }

    @Override
    public void deleteBoard(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        if(board.isEmpty())
            throw new IllegalArgumentException();

        boardRepository.deleteById(id);
    }
}
