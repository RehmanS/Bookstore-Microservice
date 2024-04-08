package com.bookstore.bookservice.mapper;

import com.bookstore.bookservice.dto.BookCreateRequest;
import com.bookstore.bookservice.dto.BookDto;
import com.bookstore.bookservice.dto.BookResponse;
import com.bookstore.bookservice.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BookMapper {
    BookDto convertBookDtoToBook(Book book);

    BookResponse convertBookToBookResponse(Book book);

    /**
     * bookCreateRequesti alir ve hal- hazirda movcud olan(databaseden gelen) Book ojectine donusturur.
     * Genellikle update senaryolarında kullanılir
     */
    Book convertBookCreateRequestToBook(@MappingTarget Book book, BookCreateRequest bookCreateRequest);

    /**
     * bookCreateRequesti alir yeni bir Book objectine donusturur ve qaytarir.
     * Yeni bir object oluşturma senaryolarında kullanılabilir.
     */
    Book convertBookCreateRequestToBook(BookCreateRequest bookCreateRequest);
}
