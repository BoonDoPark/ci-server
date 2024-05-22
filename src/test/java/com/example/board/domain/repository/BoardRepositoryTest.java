package com.example.board.domain.repository;

import com.example.board.domain.entity.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaAuditing
class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    void save() {
        // given
        Board board = new Board(1L, "asd", "aksndhjahsdjhad");

        //when
        Board savedBoard = boardRepository.save(board);

        // then
        System.out.println(savedBoard);
        Assertions.assertEquals(savedBoard.getName(), board.getName());
        Assertions.assertEquals(savedBoard.getText(), board.getText());
    }

    @Test
    void findById() {
        // given
        Board board = new Board(1L, "asd", "aksndhjahsdjhad");

        //when
        Board savedBoard = boardRepository.save(board);
        Board findBoard = boardRepository.findById(savedBoard.getId()).get();

        // then
        Assertions.assertEquals(findBoard.getName(), board.getName());
        Assertions.assertEquals(findBoard.getText(), board.getText());
    }
}