package io.swagger.api;

import io.swagger.model.Comment;
import io.swagger.model.InlineResponse200;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.CommentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-24T07:28:53.682475384Z[GMT]")
@RestController
@RequiredArgsConstructor
public class CommentApiController implements CommentApi {

    private static final Logger log = LoggerFactory.getLogger(CommentApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final CommentService commentService;

    public ResponseEntity<Comment> addComment(@Parameter(in = ParameterIn.HEADER, description = "Authorization token" ,required=true,schema=@Schema()) @RequestHeader(value="Authorization", required=true) String authorization
,@Parameter(in = ParameterIn.DEFAULT, description = "Create a new comment", required=true, schema=@Schema()) @Valid @RequestBody Comment body
) {
        return ResponseEntity.ok(commentService.addComment(body));
    }

    public ResponseEntity<Void> deleteComment(@Parameter(in = ParameterIn.PATH, description = "Comment id to delete", required=true, schema=@Schema()) @PathVariable("commentId") Long commentId
,@Parameter(in = ParameterIn.HEADER, description = "Authorization token" ,required=true,schema=@Schema()) @RequestHeader(value="Authorization", required=true) String authorization
) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<InlineResponse200> getAllComments(@Parameter(in = ParameterIn.QUERY, description = "Page number (starts from 0)" ,schema=@Schema( defaultValue="0")) @Valid @RequestParam(value = "page", required = false, defaultValue="0") Integer page
,@Parameter(in = ParameterIn.QUERY, description = "Number of items per page" ,schema=@Schema( defaultValue="20")) @Valid @RequestParam(value = "size", required = false, defaultValue="20") Integer size
,@Parameter(in = ParameterIn.QUERY, description = "Sort field" ,schema=@Schema(allowableValues={ "raiting", "creationDate", "editDate" }
, defaultValue="creationDate")) @Valid @RequestParam(value = "sortBy", required = false, defaultValue="creationDate") String sortBy
,@Parameter(in = ParameterIn.QUERY, description = "Sort direction" ,schema=@Schema(allowableValues={ "asc", "desc" }
, defaultValue="desc")) @Valid @RequestParam(value = "sortDirection", required = false, defaultValue="desc") String sortDirection
) {
        InlineResponse200 response = commentService.getAllComments(page, size, sortBy, sortDirection);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getCommentById(@Parameter(in = ParameterIn.PATH, description = "ID of comment to return", required=true, schema=@Schema()) @PathVariable("commentId") Long commentId
) {
        try {
            return ResponseEntity.ok(commentService.getComment(commentId));
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(404, ex.getMessage()));
        }
    }

    public ResponseEntity<?> updateCommentById(@Parameter(in = ParameterIn.HEADER, description = "Authorization token" ,required=true,schema=@Schema()) @RequestHeader(value="Authorization", required=true) String authorization
,@Parameter(in = ParameterIn.PATH, description = "ID of comment to update", required=true, schema=@Schema()) @PathVariable("commentId") Long commentId
,@Parameter(in = ParameterIn.DEFAULT, description = "Update an existent comment", required=true, schema=@Schema()) @Valid @RequestBody Comment body
) {
        try {
            return ResponseEntity.ok(commentService.updateComment(commentId, body));
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(404, ex.getMessage()));
        }
    }
}
