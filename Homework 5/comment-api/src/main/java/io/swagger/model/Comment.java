package io.swagger.model;

import java.time.LocalDateTime;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.threeten.bp.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import io.swagger.configuration.NotUndefined;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Comment
 */
@Validated
@NotUndefined
@Entity
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-24T07:28:53.682475384Z[GMT]")


public class Comment   {
  @JsonProperty("id")
  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @JsonSetter(nulls = Nulls.FAIL)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id = null;

  @JsonProperty("text")
  private String text = null;

  @JsonProperty("raiting")
  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @JsonSetter(nulls = Nulls.FAIL)
  private Integer raiting = null;

  @JsonProperty("creationDate")
  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  @Column(updatable = false)
  private LocalDateTime creationDate = null;

  @JsonProperty("editDate")
  @JsonInclude(JsonInclude.Include.NON_ABSENT)
  private LocalDateTime editDate = null;

  @JsonProperty("userId")
  private Long userId = null;

  @PrePersist
  protected void onCreate() {
    if (creationDate == null) {
      creationDate = LocalDateTime.now();
    }
    editDate = creationDate; // При создании editDate равно creationDate
  }

  @PreUpdate
  protected void onUpdate() {
    editDate = LocalDateTime.now();
  }
  public Comment id(Long id) { 

    this.id = id;
    return this;
  }

  /**
   * Comment unique identifier
   * @return id
   **/
  
  @Schema(example = "10", description = "Comment unique identifier")
  
  public Long getId() {  
    return id;
  }



  public void setId(Long id) { 
    this.id = id;
  }

  public Comment text(String text) { 

    this.text = text;
    return this;
  }

  /**
   * Content of the comment
   * @return text
   **/
  
  @Schema(example = "This is a great post!", required = true, description = "Content of the comment")
  
  @NotNull
  public String getText() {  
    return text;
  }



  public void setText(String text) { 

    this.text = text;
  }

  public Comment raiting(Integer raiting) {

    this.raiting = raiting;
    return this;
  }

  /**
   * Number of likes for the comment
   * @return raiting
   **/
  
  @Schema(example = "5", description = "Number of likes for the comment")
  
  public Integer getRaiting() {
    return raiting;
  }



  public void setRaiting(Integer raiting) {
    this.raiting = raiting;
  }

  public Comment creationDate(LocalDateTime creationDate) { 

    this.creationDate = creationDate;
    return this;
  }

  /**
   * Date and time when the comment was created
   * @return creationDate
   **/
  
  @Schema(example = "2025-03-19T14:30Z", description = "Date and time when the comment was created")
  
@Valid
  public LocalDateTime getCreationDate() {  
    return creationDate;
  }



  public void setCreationDate(LocalDateTime creationDate) { 
    this.creationDate = creationDate;
  }

  public Comment editDate(LocalDateTime editDate) { 

    this.editDate = editDate;
    return this;
  }

  /**
   * Date and time when the comment was last edited
   * @return editDate
   **/
  
  @Schema(example = "2025-03-19T15:45Z", description = "Date and time when the comment was last edited")
  
@Valid
  public LocalDateTime getEditDate() {  
    return editDate;
  }



  public void setEditDate(LocalDateTime editDate) {
    this.editDate = editDate;
  }

  public Comment userId(Long userId) { 

    this.userId = userId;
    return this;
  }

  /**
   * ID of the user who created the comment
   * @return userId
   **/
  
  @Schema(example = "42", required = true, description = "ID of the user who created the comment")
  
  @NotNull
  public Long getUserId() {  
    return userId;
  }



  public void setUserId(Long userId) { 

    this.userId = userId;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Comment comment = (Comment) o;
    return Objects.equals(this.id, comment.id) &&
        Objects.equals(this.text, comment.text) &&
        Objects.equals(this.raiting, comment.raiting) &&
        Objects.equals(this.creationDate, comment.creationDate) &&
        Objects.equals(this.editDate, comment.editDate) &&
        Objects.equals(this.userId, comment.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, raiting, creationDate, editDate, userId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Comment {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    raiting: ").append(toIndentedString(raiting)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    editDate: ").append(toIndentedString(editDate)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
