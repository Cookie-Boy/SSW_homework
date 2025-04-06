package io.swagger.integration;

import io.swagger.api.NotFoundException;
import io.swagger.model.Comment;
import io.swagger.model.InlineResponse200;
import io.swagger.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
class CommentServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private CommentService commentService;

    private Comment testComment;

    @BeforeEach
    void setUp() {
        testComment = new Comment();
        testComment.setText("Integration test comment");
        testComment.setUserId(1L);
        testComment.setCreationDate(LocalDateTime.now());
    }

    @Test
    void shouldSaveAndRetrieveComment() throws NotFoundException {
        Comment savedComment = commentService.addComment(testComment);
        assertNotNull(savedComment.getId());

        Comment retrievedComment = commentService.getComment(savedComment.getId());
        assertEquals(savedComment.getId(), retrievedComment.getId());
        assertEquals(savedComment.getText(), retrievedComment.getText());
    }

    @Test
    void shouldUpdateComment() throws NotFoundException {
        Comment savedComment = commentService.addComment(testComment);
        assertNotNull(savedComment.getId());

        Comment updateData = new Comment();
        updateData.setText("Updated text");
        updateData.setRaiting(5);

        Comment updatedComment = commentService.updateComment(savedComment.getId(), updateData);

        assertEquals("Updated text", updatedComment.getText());
        assertEquals(5, updatedComment.getRaiting());
        assertNotNull(updatedComment.getEditDate());
    }

    @Test
    void shouldDeleteComment() {
        Comment savedComment = commentService.addComment(testComment);
        assertNotNull(savedComment.getId());

        assertDoesNotThrow(() -> commentService.deleteComment(savedComment.getId()));
        assertThrows(NotFoundException.class, () -> commentService.getComment(savedComment.getId()));
    }

    @Test
    void shouldReturnPaginatedComments() {
        // Clear previous data
        for (int i = 0; i < 15; i++) {
            Comment comment = new Comment();
            comment.setText("Comment " + i);
            comment.setUserId((long) i);
            commentService.addComment(comment);
        }

        InlineResponse200 response = commentService.getAllComments(0, 10, "creationDate", "desc");

        assertNotNull(response);
        assertEquals(10, response.getContent().size());
        assertTrue(response.getTotalPages() > 1);
    }

    @Test
    void shouldThrowNotFoundExceptionForNonExistingComment() {
        assertThrows(NotFoundException.class, () -> commentService.getComment(999L));
    }
}