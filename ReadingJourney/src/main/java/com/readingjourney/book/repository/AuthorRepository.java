package com.readingjourney.book.repository;

import com.readingjourney.book.entity.Author;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on Author entities. This interface provides
 * methods for accessing and managing Author entities in the database.
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

  @EntityGraph(value = "Author.books")
  List<Author> findAll();

  @EntityGraph(value = "Author.books")
  Optional<Author> findById(Long id);

}
