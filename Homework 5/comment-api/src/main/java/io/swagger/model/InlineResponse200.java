package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.model.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.openapitools.jackson.nullable.JsonNullable;
import io.swagger.configuration.NotUndefined;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * InlineResponse200
 */
@Validated
@NotUndefined
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-24T07:28:53.682475384Z[GMT]")
@AllArgsConstructor
public class InlineResponse200 {
  @JsonProperty("content")
  @Valid
  private List<Comment> content = null;
  @JsonProperty("totalElements")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Integer totalElements = null;

  @JsonProperty("totalPages")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Integer totalPages = null;

  @JsonProperty("currentPage")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Integer currentPage = null;

  @JsonProperty("size")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Integer size = null;


  public InlineResponse200 content(List<Comment> content) { 

    this.content = content;
    return this;
  }

  public InlineResponse200 addContentItem(Comment contentItem) {
    if (this.content == null) {
      this.content = new ArrayList<Comment>();
    }
    this.content.add(contentItem);
    return this;
  }

  /**
   * Get content
   * @return content
   **/
  
  @Schema(description = "")
  @Valid
  public List<Comment> getContent() {  
    return content;
  }



  public void setContent(List<Comment> content) { 
    this.content = content;
  }

  public InlineResponse200 totalElements(Integer totalElements) { 

    this.totalElements = totalElements;
    return this;
  }

  /**
   * Get totalElements
   * @return totalElements
   **/
  
  @Schema(description = "")
  
  public Integer getTotalElements() {  
    return totalElements;
  }



  public void setTotalElements(Integer totalElements) { 
    this.totalElements = totalElements;
  }

  public InlineResponse200 totalPages(Integer totalPages) { 

    this.totalPages = totalPages;
    return this;
  }

  /**
   * Get totalPages
   * @return totalPages
   **/
  
  @Schema(description = "")
  
  public Integer getTotalPages() {  
    return totalPages;
  }



  public void setTotalPages(Integer totalPages) { 
    this.totalPages = totalPages;
  }

  public InlineResponse200 currentPage(Integer currentPage) { 

    this.currentPage = currentPage;
    return this;
  }

  /**
   * Get currentPage
   * @return currentPage
   **/
  
  @Schema(description = "")
  
  public Integer getCurrentPage() {  
    return currentPage;
  }



  public void setCurrentPage(Integer currentPage) { 
    this.currentPage = currentPage;
  }

  public InlineResponse200 size(Integer size) { 

    this.size = size;
    return this;
  }

  /**
   * Get size
   * @return size
   **/
  
  @Schema(description = "")
  
  public Integer getSize() {  
    return size;
  }



  public void setSize(Integer size) { 
    this.size = size;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse200 inlineResponse200 = (InlineResponse200) o;
    return Objects.equals(this.content, inlineResponse200.content) &&
        Objects.equals(this.totalElements, inlineResponse200.totalElements) &&
        Objects.equals(this.totalPages, inlineResponse200.totalPages) &&
        Objects.equals(this.currentPage, inlineResponse200.currentPage) &&
        Objects.equals(this.size, inlineResponse200.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(content, totalElements, totalPages, currentPage, size);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200 {\n");
    
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    totalElements: ").append(toIndentedString(totalElements)).append("\n");
    sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
    sb.append("    currentPage: ").append(toIndentedString(currentPage)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
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
