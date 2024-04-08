package com.bookstore.bookservice.serviceImpl;

import com.bookstore.bookservice.dto.BookCreateRequest;
import com.bookstore.bookservice.dto.BookDto;
import com.bookstore.bookservice.dto.BookResponse;
import com.bookstore.bookservice.entity.Book;
import com.bookstore.bookservice.exception.BookAlreadyExistsException;
import com.bookstore.bookservice.exception.BookNotFoundException;
import com.bookstore.bookservice.mapper.BookMapper;
import com.bookstore.bookservice.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BookServiceImplTest{
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookServiceImpl(bookRepository, bookMapper);
    }

    @Test
    void createBook_BookDoesNotExist_SuccessfullyCreatesBook() {
        // Arrange
        BookCreateRequest bookCreateRequest = createBookCreateRequest();
        when(bookRepository.findBookByIsbn(bookCreateRequest.isbn())).thenReturn(null);

        // Act
        assertDoesNotThrow(() -> bookService.createBook(bookCreateRequest));

        // Assert
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void createBook_BookAlreadyExists_ThrowsBookAlreadyExistsException() {
        // Arrange
        BookCreateRequest bookCreateRequest = createBookCreateRequest();
        Book existingBook = new Book();
        when(bookRepository.findBookByIsbn(bookCreateRequest.isbn())).thenReturn(existingBook);

        // Act & Assert
        assertThrows(BookAlreadyExistsException.class, () -> bookService.createBook(bookCreateRequest));
    }

    @Test
    void getAllBooks_ReturnsListOfAllBooks() {
        // Arrange
        List<Book> books = createBookList();
        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<BookDto> bookDtos = bookService.getAllBooks();

        // Assert
        assertEquals(books.size(), bookDtos.size());

    }

    @Test
    void getBookByID_BookExists_ReturnsCorrectBookDto() {
        // Arrange
        Long bookId = 1L;
        Book book = createBook();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Act
        BookDto bookDto = bookService.getBookByID(bookId);

        // Assert
        assertNotNull(bookDto);
        assertEquals(book.getName(), bookDto.getName());
        assertEquals(book.getAuthor(), bookDto.getAuthor());
        // Additional assertions can be made to compare other fields
    }

    @Test
    void getBookByID_BookDoesNotExist_ThrowsBookNotFoundException() {
        // Arrange
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BookNotFoundException.class, () -> bookService.getBookByID(bookId));
    }

    @Test
    void testGetBookByIsbn_Successfully() {
        // Arrange
        String isbn = "1234567890";
        Book book = createBook();
        when(bookRepository.getBookByIsbn(isbn)).thenReturn(Optional.of(book));

        // Act
        BookDto result = bookService.getBookByIsbn(isbn);

        // Assert
        assertEquals(book.getId(), result.getId());
        assertEquals(book.getName(), result.getName());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getBookYear(), result.getBookYear());
        assertEquals(book.getIsbn(), result.getIsbn());
        assertEquals(book.getFiction(), result.getFiction());
        assertEquals(book.getDetail(), result.getDetail());
    }

    @Test
    void testGetBookByIsbn_BookNotFound() {
        // Arrange
        String isbn = "1234567890";
        when(bookRepository.getBookByIsbn(isbn)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BookNotFoundException.class, () -> bookService.getBookByIsbn(isbn));
    }

//    @Test
//    void testDeleteBookByIsbn() {
//        // Arrange
//        String isbn = "1234567890";
//        Book book = createBook();
//        when(bookRepository.getBookByIsbn(isbn)).thenReturn(Optional.of(book));
//
//        // Act
//        bookService.deleteBookByIsbn(isbn);
//
//        // Assert
//        verify(bookRepository).deleteBookByIsbn(isbn);
//    }

    @Test
    void testDeleteBookByIsbn_BookNotFound() {
        // Arrange
        String isbn = "1234567890";
        when(bookRepository.getBookByIsbn(isbn)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBookByIsbn(isbn));
        verify(bookRepository, never()).deleteBookByIsbn(isbn);
    }

    @Test
    void testUpdateBookByIsbn() {
        // Arrange
        String isbn = "1234567890";
        BookCreateRequest bookCreateRequest = createBookCreateRequest();
        Book existingBook = createBook();
        when(bookRepository.getBookByIsbn(isbn)).thenReturn(Optional.of(existingBook));

        // Act
        BookResponse result = bookService.updateBookByIsbn(bookCreateRequest);

        // Assert
        assertEquals(bookCreateRequest.name(), existingBook.getName());
        assertEquals(bookCreateRequest.author(), existingBook.getAuthor());
        assertEquals(bookCreateRequest.bookYear(), existingBook.getBookYear());
        assertEquals(bookCreateRequest.isbn(), existingBook.getIsbn());
        assertEquals(bookCreateRequest.fiction(), existingBook.getFiction());
        assertEquals(bookCreateRequest.detail(), existingBook.getDetail());

        verify(bookRepository).save(existingBook);

        assertEquals(bookCreateRequest.name(), result.name());
        assertEquals(bookCreateRequest.author(), result.author());
        assertEquals(bookCreateRequest.bookYear(), result.bookYear());
        assertEquals(bookCreateRequest.isbn(), result.isbn());
        assertEquals(bookCreateRequest.fiction(), result.fiction());
        assertEquals(bookCreateRequest.detail(), result.detail());
    }

    @Test
    void testFindBookByName() {
        // Arrange
        String searchText = "sample";
        List<Book> books = createBookList();
        when(bookRepository.findBookByName(searchText)).thenReturn(books);

        // Act
        List<BookResponse> result = bookService.findBookByName(searchText);

        // Assert
        assertEquals(books.size(), result.size());

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            BookResponse bookResponse = result.get(i);

            assertEquals(book.getName(), bookResponse.name());
            assertEquals(book.getAuthor(), bookResponse.author());
            assertEquals(book.getBookYear(), bookResponse.bookYear());
            assertEquals(book.getIsbn(), bookResponse.isbn());
            assertEquals(book.getFiction(), bookResponse.fiction());
            assertEquals(book.getDetail(), bookResponse.detail());
        }
    }

    @Test
    void testFindBookByAuthor() {
        // Arrange
        String searchAuthor = "John Doe";
        List<Book> books = createBookList();
        when(bookRepository.findBooksByAuthor(searchAuthor)).thenReturn(books);

        // Act
        List<BookResponse> result = bookService.findBookByAuthor(searchAuthor);

        // Assert
        assertEquals(books.size(), result.size());

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            BookResponse bookResponse = result.get(i);

            assertEquals(book.getName(), bookResponse.name());
            assertEquals(book.getAuthor(), bookResponse.author());
            assertEquals(book.getBookYear(), bookResponse.bookYear());
            assertEquals(book.getIsbn(), bookResponse.isbn());
            assertEquals(book.getFiction(), bookResponse.fiction());
            assertEquals(book.getDetail(), bookResponse.detail());
        }
    }

    private List<Book> createBookList() {
        List<Book> books = new ArrayList<>();

        books.add(new Book(1L,"image1","sample book1","author1",1900,"isbn1","fiction1","detail1"));
        books.add(new Book(2L,"image2","sample book2","author2",1901,"isbn2","fiction2","detail2"));
        books.add(new Book(3L,"image3","sample book3","author3",1902,"isbn3","fiction3","detail3"));

        return books;
    }

    private Book createBook() {
       return new Book(1L,"image","name1","author1",1900,"1234567890","fiction1","detail1");
    }

    private BookCreateRequest createBookCreateRequest() {
        return BookCreateRequest.builder()
                .name("sample book")
                .author("John Doe")
                .bookYear(2000)
                .fiction("fiction")
                .detail("detail")
                .isbn("1234567890")
                .build();
    }
}