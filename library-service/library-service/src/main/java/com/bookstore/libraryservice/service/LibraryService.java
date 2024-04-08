package com.bookstore.libraryservice.service;

import com.bookstore.libraryservice.dto.AddBookRequest;
import com.bookstore.libraryservice.dto.CreateLibraryRequest;
import com.bookstore.libraryservice.dto.LibraryDto;
import com.bookstore.libraryservice.dto.LibraryResponse;

import java.util.List;

public interface LibraryService {

    List<LibraryResponse> getAllLibraries();

    LibraryDto getAllBooksInLibraryById(Long id);

    LibraryDto createLibrary(CreateLibraryRequest createLibraryRequest);

    void addBookToLibrary(AddBookRequest addBookRequest);

    void deleteLibraryById(Long id);

    void deleteBookFromLibrary(Long libId,Long bookId);


}
