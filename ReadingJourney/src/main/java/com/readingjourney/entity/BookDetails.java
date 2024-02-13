package com.readingjourney.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "year_publication", nullable = false)
  private LocalDate yearPublication;

  @Column(name = "number_pages", nullable = false)
  private Integer numberPages;

  @Column(name = "genre", length = 100, nullable = false)
  private String genre;

  @Column(name = "format", length = 100, nullable = false)
  private String format;

  @Column(name = "edition", length = 100, nullable = false)
  private String edition;

  @Column(name = "isbn", nullable = false)
  private Long isbn;

  @OneToOne(mappedBy = "bookDetails")
  private Book book;


}
