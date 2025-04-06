package io.swagger.service;

import io.swagger.api.NotFoundException;
import io.swagger.model.Comment;
import io.swagger.model.InlineResponse200;
import io.swagger.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public InlineResponse200 getAllComments(Integer page, Integer size, String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, sortBy)
        );

        Page<Comment> commentPage = commentRepository.findAll(pageable);

        return new InlineResponse200(commentPage.getContent(),
                commentPage.getTotalPages(),
                commentPage.getTotalPages(),
                commentPage.getNumber(),
                commentPage.getSize());
    }

    public Comment getComment(Long commentId) throws NotFoundException {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(404, "Comment with ID " + commentId + " not found."));
    }

    public Comment updateComment(Long commentId, Comment updatedComment) throws NotFoundException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException(404, "Comment with ID " + commentId + " not found."));

        // Определить userId по sessionId, но такой системы нет

        comment.setText(Objects.requireNonNullElse(updatedComment.getText(), comment.getText()));
        comment.setRaiting(Objects.requireNonNullElse(updatedComment.getRaiting(), comment.getRaiting()));
        comment.setEditDate(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
