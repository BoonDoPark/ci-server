package com.example.board;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardApplicationTests {
	@Value("${spring.datasource.username}")
	String username;

    @Test
	void contextLoads() {
	}

	@Test
    void dbUsernameIsSa() {
		Assertions.assertEquals("sa", username);
	}

}
