package io.swagger.api;

import io.swagger.model.Comment;
import io.swagger.model.InlineResponse200;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-24T07:28:53.682475384Z[GMT]")
@RestController
public class CommentApiController implements CommentApi {

    private static final Logger log = LoggerFactory.getLogger(CommentApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private final CommentService commentService;

    @org.springframework.beans.factory.annotation.Autowired
    public CommentApiController(ObjectMapper objectMapper, HttpServletRequest request, CommentService commentService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.commentService = commentService;
    }

    public ResponseEntity<Comment> addComment(@Parameter(in = ParameterIn.HEADER, description = "Authorization token" ,required=true,schema=@Schema()) @RequestHeader(value="Authorization", required=true) String authorization
,@Parameter(in = ParameterIn.DEFAULT, description = "Create a new comment", required=true, schema=@Schema()) @Valid @RequestBody Comment body
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return ResponseEntity.ok(commentService.addComment(body));
            } catch (Exception e) {
                log.error("Something happened: ", e);
                return new ResponseEntity<Comment>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Comment>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteComment(@Parameter(in = ParameterIn.PATH, description = "Comment id to delete", required=true, schema=@Schema()) @PathVariable("commentId") Long commentId
,@Parameter(in = ParameterIn.HEADER, description = "Authorization token" ,required=true,schema=@Schema()) @RequestHeader(value="Authorization", required=true) String authorization
) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<InlineResponse200> getAllComments(@Parameter(in = ParameterIn.QUERY, description = "Page number (starts from 0)" ,schema=@Schema( defaultValue="0")) @Valid @RequestParam(value = "page", required = false, defaultValue="0") Integer page
,@Parameter(in = ParameterIn.QUERY, description = "Number of items per page" ,schema=@Schema( defaultValue="20")) @Valid @RequestParam(value = "size", required = false, defaultValue="20") Integer size
,@Parameter(in = ParameterIn.QUERY, description = "Sort field" ,schema=@Schema(allowableValues={ "raiting", "creationDate", "editDate" }
, defaultValue="creationDate")) @Valid @RequestParam(value = "sortBy", required = false, defaultValue="creationDate") String sortBy
,@Parameter(in = ParameterIn.QUERY, description = "Sort direction" ,schema=@Schema(allowableValues={ "asc", "desc" }
, defaultValue="desc")) @Valid @RequestParam(value = "sortDirection", required = false, defaultValue="desc") String sortDirection
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<InlineResponse200>(objectMapper.readValue("{\n  \"size\" : 5,\n  \"totalPages\" : 6,\n  \"currentPage\" : 1,\n  \"content\" : [ {\n    \"raiting\" : 5,\n    \"id\" : 10,\n    \"text\" : \"This is a great post!\",\n    \"creationDate\" : \"2025-03-19T14:30:00Z\",\n    \"editDate\" : \"2025-03-19T15:45:00Z\",\n    \"userId\" : 42\n  }, {\n    \"raiting\" : 5,\n    \"id\" : 10,\n    \"text\" : \"This is a great post!\",\n    \"creationDate\" : \"2025-03-19T14:30:00Z\",\n    \"editDate\" : \"2025-03-19T15:45:00Z\",\n    \"userId\" : 42\n  } ],\n  \"totalElements\" : 0\n}", InlineResponse200.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<InlineResponse200>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<InlineResponse200>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Comment> getCommentById(@Parameter(in = ParameterIn.PATH, description = "ID of comment to return", required=true, schema=@Schema()) @PathVariable("commentId") Long commentId
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Comment>(objectMapper.readValue("{\n  \"raiting\" : 5,\n  \"id\" : 10,\n  \"text\" : \"This is a great post!\",\n  \"creationDate\" : \"2025-03-19T14:30:00Z\",\n  \"editDate\" : \"2025-03-19T15:45:00Z\",\n  \"userId\" : 42\n}", Comment.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Comment>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Comment>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Comment> updateCommentById(@Parameter(in = ParameterIn.HEADER, description = "Authorization token" ,required=true,schema=@Schema()) @RequestHeader(value="Authorization", required=true) String authorization
,@Parameter(in = ParameterIn.PATH, description = "ID of comment to update", required=true, schema=@Schema()) @PathVariable("commentId") Long commentId
,@Parameter(in = ParameterIn.DEFAULT, description = "Update an existent comment", required=true, schema=@Schema()) @Valid @RequestBody Comment body
) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<Comment>(objectMapper.readValue("{\n  \"raiting\" : 5,\n  \"id\" : 10,\n  \"text\" : \"This is a great post!\",\n  \"creationDate\" : \"2025-03-19T14:30:00Z\",\n  \"editDate\" : \"2025-03-19T15:45:00Z\",\n  \"userId\" : 42\n}", Comment.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<Comment>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<Comment>(HttpStatus.NOT_IMPLEMENTED);
    }

}
