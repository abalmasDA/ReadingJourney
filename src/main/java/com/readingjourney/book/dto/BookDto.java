package com.readingjourney.book.dto;


import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

  @NotBlank(message = "Title must not be blank")
  @Size(max = 255, message = "Title must not exceed 255 characters")
  private String title;

  @NotNull(message = "Rating must not be null")
  @Min(value = 1, message = "Rating must be at least 1")
  @Max(value = 10, message = "Rating must be no more than 10")
  private Long rating;

  @PastOrPresent(message = "Year of publication must be in the past or present")
  private LocalDate yearPublication;

  @NotNull(message = "Number of pages must not be null")
  @Positive(message = "Number of pages must be positive")
  private Integer numberPages;

  @NotBlank(message = "Genre must not be blank")
  private String genre;

  @NotBlank(message = "Format must not be blank")
  private String format;

  @NotBlank(message = "Edition must not be blank")
  private String edition;

  @NotNull(message = "ISBN must not be null")
  @Digits(integer = 13, fraction = 0, message = "ISBN must be a valid 13 digit number")
  private Long isbn;

}

