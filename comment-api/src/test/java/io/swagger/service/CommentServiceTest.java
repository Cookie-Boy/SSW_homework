package io.swagger.api;

import io.swagger.model.Comment;
import io.swagger.model.InlineResponse200;
import io.swagger.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CommentApiControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private CommentApiController commentApiController;

    private final String TEST_AUTH_TOKEN = "Bearer test-token";
    private final Long TEST_COMMENT_ID = 1L;
    private Comment testComment;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test comment
        testComment = new Comment();
        testComment.setId(TEST_COMMENT_ID);
        testComment.setText("Test comment");
        testComment.setUserId(123L);
        testComment.setCreationDate(LocalDateTime.now());

        // Mock request
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
    }

    @Test
    void addComment_ShouldReturnCreatedComment() {
        when(commentService.addComment(any(Comment.class))).thenReturn(testComment);

        ResponseEntity<Comment> response = commentApiController.addComment(TEST_AUTH_TOKEN, testComment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(TEST_COMMENT_ID, response.getBody().getId());
        verify(commentService, times(1)).addComment(any(Comment.class));
    }

    @Test
    void deleteComment_ShouldReturnOk() {
        doNothing().when(commentService).deleteComment(TEST_COMMENT_ID);

        ResponseEntity<Void> response = commentApiController.deleteComment(TEST_COMMENT_ID, TEST_AUTH_TOKEN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(commentService, times(1)).deleteComment(TEST_COMMENT_ID);
    }

    @Test
    void getAllComments_ShouldReturnPaginatedComments() {
        InlineResponse200 mockResponse = new InlineResponse200();
        mockResponse.setContent(Collections.singletonList(testComment));

        when(commentService.getAllComments(0, 20, "creationDate", "desc"))
                .thenReturn(mockResponse);

        ResponseEntity<InlineResponse200> response = commentApiController.getAllComments(0, 20, "creationDate", "desc");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    void getCommentById_ShouldReturnComment() throws NotFoundException {
        when(commentService.getComment(TEST_COMMENT_ID)).thenReturn(testComment);

        ResponseEntity<?> response = commentApiController.getCommentById(TEST_COMMENT_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(Comment.class, response.getBody());
        assertEquals(TEST_COMMENT_ID, ((Comment) response.getBody()).getId());
    }

    @Test
    void getCommentById_ShouldReturnNotFound() throws NotFoundException {
        when(commentService.getComment(TEST_COMMENT_ID))
                .thenThrow(new NotFoundException(404, "Comment not found"));

        ResponseEntity<?> response = commentApiController.getCommentById(TEST_COMMENT_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateCommentById_ShouldReturnUpdatedComment() throws NotFoundException {
        when(commentService.updateComment(eq(TEST_COMMENT_ID), any(Comment.class)))
                .thenReturn(testComment);

        ResponseEntity<?> response = commentApiController.updateCommentById(
                TEST_AUTH_TOKEN, TEST_COMMENT_ID, testComment);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(Comment.class, response.getBody());
    }

    @Test
    void updateCommentById_ShouldReturnNotFound() throws NotFoundException {
        when(commentService.updateComment(eq(TEST_COMMENT_ID), any(Comment.class)))
                .thenThrow(new NotFoundException(404, "Comment not found"));

        ResponseEntity<?> response = commentApiController.updateCommentById(
                TEST_AUTH_TOKEN, TEST_COMMENT_ID, testComment);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}