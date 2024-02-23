package com.readingjourney.book.mapper;

import com.readingjourney.book.dto.BookDto;
import com.readingjourney.book.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

  Book toEntity(BookDto bookDto);

  BookDto toDto(Book book);

}
