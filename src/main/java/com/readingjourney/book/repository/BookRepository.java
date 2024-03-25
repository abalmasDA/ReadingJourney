package com.readingjourney.book.repository;


import com.readingjourney.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Repository interface for performing CRUD operations on Book entities. This interface provides
 * methods for accessing and managing Book entities in the database.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

}
