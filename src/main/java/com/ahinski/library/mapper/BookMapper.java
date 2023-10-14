package com.ahinski.library.mapper;

import com.ahinski.library.dto.BookDTO;
import com.ahinski.library.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "isbn", target = "isbn")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "genre", target = "genre")
    BookDTO bookToBookDTO(Book book);

    @Mapping(source = "isbn", target = "isbn")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "genre", target = "genre")
    Book bookDTOToBook(BookDTO bookDTO);

    List<BookDTO> booksToBookDTOs(List<Book> books);

}

