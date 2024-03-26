package com.readingjourney.book.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Author dto. This class represents an author dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDto {

  @NotBlank(message = "First name must not be blank")
  @Size(min = 3, max = 50, message = "First name must be between 1 and 50 characters long")
  private String firstName;

  @NotBlank(message = "Last name must not be blank")
  @Size(min = 3, max = 50, message = "Last name must be between 1 and 50 characters long")
  private String lastName;

  @Size(max = 1500, message = "Biography must be less than 1000 characters")
  private String biography;

}
