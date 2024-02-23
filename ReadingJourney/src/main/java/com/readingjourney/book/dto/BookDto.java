package com.readingjourney.book.dto;


import com.readingjourney.book.entity.Author;
import com.readingjourney.book.entity.BookDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {

  private String title;
  private Long rating;
  private Author author;
  private BookDetails bookDetails;

}

