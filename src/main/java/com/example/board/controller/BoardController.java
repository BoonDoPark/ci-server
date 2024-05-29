package com.example.board.controller;

import com.example.board.domain.entity.Board;
import com.example.board.dto.request.BoardRequest;
import com.example.board.dto.response.BoardResponse;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*",
        allowedHeaders = "*",
        methods = {
        RequestMethod.GET,
        RequestMethod.POST,
        RequestMethod.DELETE,
        RequestMethod.PUT,
        RequestMethod.OPTIONS})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public List<BoardResponse> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("/{id}")
    public BoardResponse getBoardFromName(@PathVariable("id") Long id) {
        return boardService.getBoardFromName(id);
    }

    @PostMapping
    public void createBoard(@RequestBody BoardRequest req) {
        boardService.createBoard(req);
    }

    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
    }
}
