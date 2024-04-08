package com.bookstore.bookservice.serviceImpl;

import com.bookstore.bookservice.dto.BookCreateRequest;
import com.bookstore.bookservice.dto.BookDto;
import com.bookstore.bookservice.dto.BookResponse;
import com.bookstore.bookservice.entity.Book;
import com.bookstore.bookservice.exception.BookAlreadyExistsException;
import com.bookstore.bookservice.exception.BookNotFoundException;
import com.bookstore.bookservice.mapper.BookMapper;
import com.bookstore.bookservice.repository.BookRepository;
import com.bookstore.bookservice.service.BookService;
import com.bookstore.bookservice.service.ReadListService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private ReadListService readListService;
    private BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Autowired
    public void setReadListService(@Lazy ReadListService readListService) {
        this.readListService = readListService;
    }

    @Override
    public void createBook(BookCreateRequest bookCreateRequest) {
        Book findBook = bookRepository.findBookByIsbn(bookCreateRequest.isbn());

        if (findBook == null) {
            Book book = bookMapper.convertBookCreateRequestToBook(bookCreateRequest);
            bookRepository.save(book);
        } else {
            throw new BookAlreadyExistsException("The book already exists. isbn: " + bookCreateRequest.isbn());
        }
    }

    @Override
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> bookMapper.convertBookDtoToBook(book))
                .toList();
    }

    @Override
    public BookDto getBookByID(Long id) {
        return bookRepository.findById(id)
                .map(book -> bookMapper.convertBookDtoToBook(book))
                .orElseThrow(() -> new BookNotFoundException("Book could not found by id: " + id));
    }

    @Override
    public BookDto getBookByIsbn(String isbn) {
        return bookRepository.getBookByIsbn(isbn)
                .map(book -> bookMapper.convertBookDtoToBook(book))
                .orElseThrow(() -> new BookNotFoundException(("Book could not found by isbn: " + isbn)));
    }

    @Override
    @Transactional
    public void deleteBookByIsbn(String isbn) {
        bookRepository.getBookByIsbn(isbn).orElseThrow(() -> new BookNotFoundException("Book could not found by isbn: " + isbn));
        bookRepository.deleteBookByIsbn(isbn);
        readListService.deleteFromReadListByIsbn(isbn);
    }

    @Override
    public BookResponse updateBookByIsbn(BookCreateRequest bookCreateRequest) {
        Book findBook = bookRepository.getBookByIsbn(bookCreateRequest.isbn())
                .orElseThrow(() -> new BookNotFoundException("Book could not found by isbn: " + bookCreateRequest.isbn()));

        bookMapper.convertBookCreateRequestToBook(findBook, bookCreateRequest);
        Book book = bookRepository.save(findBook);
        return bookMapper.convertBookToBookResponse(book);
    }

    @Override
    @Transactional
    public List<BookResponse> findBookByName(String text) {
        return bookRepository.findBookByName(text).stream()
                .map(book -> bookMapper.convertBookToBookResponse(book))
                .toList();
    }

    @Override
    public List<BookResponse> findBookByAuthor(String text) {
        return bookRepository.findBooksByAuthor(text).stream()
                .map(book -> bookMapper.convertBookToBookResponse(book))
                .toList();
    }
}
