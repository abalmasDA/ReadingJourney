package com.readingjourney.book.mapper;

import com.readingjourney.book.dto.AuthorDto;
import com.readingjourney.book.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper interface for converting between Author and AuthorDto objects.
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper {
  @Mapping(target = "books", ignore = true)
  Author toEntity(AuthorDto authorDto);

  AuthorDto toDto(Author author);
}
