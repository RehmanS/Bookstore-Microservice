package com.bookstore.libraryservice.serviceImpl;

import com.bookstore.libraryservice.client.BookServiceClient;
import com.bookstore.libraryservice.dto.*;
import com.bookstore.libraryservice.entity.Library;
import com.bookstore.libraryservice.exception.BookNotFoundByIdException;
import com.bookstore.libraryservice.exception.LibraryNotFoundException;
import com.bookstore.libraryservice.repository.LibraryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class LibraryServiceTest {
    @Mock
    private LibraryRepository libraryRepository;
    @Mock
    private BookServiceClient bookServiceClient;
    @InjectMocks
    private LibraryServiceImpl libraryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllLibraries() {
        // Mocking the repository's response
        List<Library> libraryList = new ArrayList<>();
        libraryList.add(new Library(1L, "Library 1"));
        libraryList.add(new Library(2L, "Library 2"));
        when(libraryRepository.findAll()).thenReturn(libraryList);

        // Calling the service method
        List<LibraryResponse> libraryResponses = libraryService.getAllLibraries();

        // Verifying the result
        Assertions.assertEquals(2, libraryResponses.size());
        Assertions.assertEquals("Library 1", libraryResponses.get(0).libraryName());
        Assertions.assertEquals("Library 2", libraryResponses.get(1).libraryName());

        // Verifying that the repository method was called
        verify(libraryRepository, times(1)).findAll();
    }

    @Test
    void testGetAllBooksInLibraryById() {
        // Mocking the repository's response
        Library library = new Library(1L, "Library 1");
        library.setUserBooks(List.of(1L, 2L));
        when(libraryRepository.findById(1L)).thenReturn(Optional.of(library));

        // Mocking the bookServiceClient's response
        BookDto book1 = new BookDto(1L, "image1","Book 1",1900,"fiction1","detail1");
        BookDto book2 = new BookDto(2L, "image2","Book 2",1901,"fiction2","detail2");
        when(bookServiceClient.getBookById(1L)).thenReturn(book1);
        when(bookServiceClient.getBookById(2L)).thenReturn(book2);

        // Calling the service method
        LibraryDto libraryDto = libraryService.getAllBooksInLibraryById(1L);

        // Verifying the result
        Assertions.assertEquals("Library 1", libraryDto.getLibraryName());
        Assertions.assertEquals(2, libraryDto.getUserBookList().size());
        Assertions.assertEquals("Book 1", libraryDto.getUserBookList().get(0).getName());
        Assertions.assertEquals("Book 2", libraryDto.getUserBookList().get(1).getName());

        // Verifying that the repository and bookServiceClient methods were called
        verify(libraryRepository, times(1)).findById(1L);
        verify(bookServiceClient, times(1)).getBookById(1L);
        verify(bookServiceClient, times(1)).getBookById(2L);
    }

    @Test
    void testCreateLibrary() {
        // Mocking the repository's response
        Library newLibrary = new Library(1L, "New Library");
        when(libraryRepository.save(any(Library.class))).thenReturn(newLibrary);

        // Calling the service method
        CreateLibraryRequest createLibraryRequest = new CreateLibraryRequest("New Library");
        LibraryDto libraryDto = libraryService.createLibrary(createLibraryRequest);

        // Verifying the result
        //Assertions.assertEquals(1L, libraryDto.getId());
        Assertions.assertEquals("New Library", libraryDto.getLibraryName());

        // Verifying that the repository method was called
        verify(libraryRepository, times(1)).save(any(Library.class));

    }

    @Test
    void testDeleteLibraryById_LibraryExists() {
        // Mocking the repository's response
        Library library = new Library(1L, "Library 1");
        when(libraryRepository.findById(1L)).thenReturn(Optional.of(library));

        // Calling the service method
        libraryService.deleteLibraryById(1L);

        // Verifying that the repository method was called
        verify(libraryRepository, times(1)).findById(1L);
        verify(libraryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteLibraryById_LibraryDoesNotExist() {
        // Mocking the repository's response
        when(libraryRepository.findById(1L)).thenReturn(Optional.empty());

        // Calling the service method and expecting an exception
        Assertions.assertThrows(LibraryNotFoundException.class, () -> {
            libraryService.deleteLibraryById(1L);
        });

        // Verifying that the repository method was called
        verify(libraryRepository, times(1)).findById(1L);
        verify(libraryRepository, never()).deleteById(1L);
    }

    @Test
    void testDeleteBookFromLibrary_BookDoesNotExistInLibrary() {
        // Mocking the repository's response for library
        Library library = new Library(1L, "Library 1");
        library.setUserBooks(List.of(1L, 2L));  // Existing books in the library
        when(libraryRepository.findById(1L)).thenReturn(Optional.of(library));

        // Calling the service method and expecting an exception
        Assertions.assertThrows(BookNotFoundByIdException.class, () -> {
            libraryService.deleteBookFromLibrary(1L, 3L);
        });

        // Verifying that the repository method was called
        verify(libraryRepository, times(1)).findById(1L);
        verify(libraryRepository, never()).save(library);

        // Verifying the library state remains unchanged
        Assertions.assertEquals(2, library.getUserBooks().size());
        Assertions.assertTrue(library.getUserBooks().contains(1L));
        Assertions.assertTrue(library.getUserBooks().contains(2L));
    }

    @Test
    void testDeleteBookFromLibrary_LibraryDoesNotExist() {
        // Mocking the repository's response when library does not exist
        when(libraryRepository.findById(1L)).thenReturn(Optional.empty());

        // Calling the service method and expecting an exception
        Assertions.assertThrows(LibraryNotFoundException.class, () -> {
            libraryService.deleteBookFromLibrary(1L, 2L);
        });

        // Verifying that the repository method was called
        verify(libraryRepository, times(1)).findById(1L);
        verify(libraryRepository, never()).save(any());

    }
}