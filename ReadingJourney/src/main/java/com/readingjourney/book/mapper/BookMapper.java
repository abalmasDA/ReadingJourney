package com.readingjourney.book.mapper;

import com.readingjourney.book.dto.BookDto;
import com.readingjourney.book.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * Mapper interface for converting between Book and BookDto objects.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {

  @Mapping(target = "author", ignore = true)
  Book toEntity(BookDto bookDto);

  BookDto toDto(Book book);

}
