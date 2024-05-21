package com.example.board.dto.request;

import com.example.board.domain.entity.Board;

public record BoardRequest(
        String name,
        String text
) {

    public Board toEntity() {
        return Board.builder()
                .name(name)
                .text(text)
                .build();
    }
}
