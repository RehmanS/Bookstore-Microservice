package com.bookstore.libraryservice.serviceImpl;

import com.bookstore.libraryservice.client.BookServiceClient;
import com.bookstore.libraryservice.dto.AddBookRequest;
import com.bookstore.libraryservice.dto.CreateLibraryRequest;
import com.bookstore.libraryservice.dto.LibraryDto;
import com.bookstore.libraryservice.dto.LibraryResponse;
import com.bookstore.libraryservice.entity.Library;
import com.bookstore.libraryservice.exception.BookAlreadyExistInLibraryException;
import com.bookstore.libraryservice.exception.BookNotFoundByIdException;
import com.bookstore.libraryservice.exception.LibraryNotFoundException;
import com.bookstore.libraryservice.mapper.LibraryMapper;
import com.bookstore.libraryservice.repository.LibraryRepository;
import com.bookstore.libraryservice.service.LibraryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.awt.print.Book;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {
    private final LibraryRepository libraryRepository;
    private final BookServiceClient bookServiceClient;
    private final LibraryMapper libraryMapper;

    @Override
    public List<LibraryResponse> getAllLibraries() {
        return libraryRepository.findAll().stream().map(this::convertLibraryToLibraryResponse).toList();
    }

    @Override
    public LibraryDto getAllBooksInLibraryById(Long id) {
        Library library = libraryRepository.findById(id).orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + id));

        return LibraryDto.builder()
                .libraryName(library.getLibraryName())
                .id(library.getId())
                .userBookList(library.getUserBooks()
                        .stream()
                        .map(bookServiceClient::getBookById)
                        .toList())
                .build();
    }

    @Override
    public LibraryDto createLibrary(CreateLibraryRequest createLibraryRequest) {
        Library newLibrary = new Library();
        newLibrary.setLibraryName(createLibraryRequest.libraryName());
        libraryRepository.save(newLibrary);
        return libraryMapper.convertLibraryToLibraryDto(newLibrary);
    }

    @Override
    @Transactional
    public void addBookToLibrary(AddBookRequest addBookRequest) {
        Long bookId = Objects.requireNonNull(bookServiceClient.getBookByIsbn(addBookRequest.getIsbn()).getBody()).getId();
        Library library = libraryRepository.findById(addBookRequest.getLibraryId()).
                orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + addBookRequest.getLibraryId()));
        if (library.getUserBooks().contains(bookId) & bookId != -1) {
            throw new BookAlreadyExistInLibraryException("Book already exist in Library. isbn: " + addBookRequest.getIsbn());
        } else {
            library.getUserBooks().add(bookId);
            libraryRepository.save(library);
        }
    }

    @Override
    public void deleteLibraryById(Long id) {
        Library library = libraryRepository.findById(id).orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + id));
        libraryRepository.deleteById(id);
    }

    @Override
    public void deleteBookFromLibrary(Long libId, Long bookId) {
        Library library = libraryRepository.findById(libId).orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + libId));
        List<Long> userBooks = library.getUserBooks();
        if (userBooks.contains(bookId)) {
            userBooks.remove(bookId);
            libraryRepository.save(library);
        } else {
            throw new BookNotFoundByIdException("Book could not found by id: " + bookId);
        }
    }

    private LibraryResponse convertLibraryToLibraryResponse(Library library) {
        return LibraryResponse.builder()
                .id(library.getId())
                .libraryName(library.getLibraryName())
                .build();
    }
}