package com.readingjourney.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", length = 100, nullable = false)
  private String title;

  @Column(name = "rating", nullable = false)
  private Long rating;

  @ManyToOne
  @JoinColumn(name = "author_id", nullable = false)
  private Author author;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "book_details_id", unique = true, nullable = false)
  private BookDetails bookDetails;

}
