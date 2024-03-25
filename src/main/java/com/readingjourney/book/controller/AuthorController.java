package com.readingjourney.book.controller;

import com.readingjourney.book.dto.AuthorDto;
import com.readingjourney.book.entity.Author;
import com.readingjourney.book.service.AuthorService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing authors. This controller provides endpoints for retrieving, adding,
 * updating, and deleting authors.
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @GetMapping
  public List<Author> findAll() {
    return authorService.findAll();
  }

  @GetMapping("/{id}")
  public Optional<Author> findById(@PathVariable("id") long id) {
    return authorService.findById(id);
  }

  @PostMapping()
  public Author add(@Valid @RequestBody AuthorDto authorDto) {
    return authorService.save(authorDto);
  }

  @PutMapping("/{id}")
  public Author update(@PathVariable("id") long id, @Valid @RequestBody AuthorDto authorDto) {
    return authorService.update(id, authorDto);
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable("id") long id) {
    authorService.delete(id);
  }

}


