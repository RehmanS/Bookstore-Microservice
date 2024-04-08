package com.bookstore.libraryservice.controller;

import com.bookstore.libraryservice.dto.AddBookRequest;
import com.bookstore.libraryservice.dto.CreateLibraryRequest;
import com.bookstore.libraryservice.dto.LibraryDto;
import com.bookstore.libraryservice.dto.LibraryResponse;
import com.bookstore.libraryservice.service.LibraryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping
    public List<LibraryResponse> getAllLibraries() {
        return libraryService.getAllLibraries();
    }

    @GetMapping("/{id}")
    public LibraryDto getLibraryById(@PathVariable("id") Long id) {
        return libraryService.getAllBooksInLibraryById(id);
    }

    @PostMapping
    public LibraryDto createLibrary(@RequestBody CreateLibraryRequest createLibraryRequest) {
        return libraryService.createLibrary(createLibraryRequest);
    }

    @PostMapping("/addBook")
    public ResponseEntity<String> addBookToLibrary(@Valid @RequestBody AddBookRequest addBookRequest) {
        libraryService.addBookToLibrary(addBookRequest);
        return ResponseEntity.ok("The book was successfully added to the library");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLibraryById(@PathVariable("id") Long id) {
        libraryService.deleteLibraryById(id);
        return ResponseEntity.ok("Library successfully deleted");
    }

    @DeleteMapping("/libraryId/{libId}/bookId/{bookId}")
    public ResponseEntity<String> deleteBookFromLibrary(@PathVariable("libId") Long libId, @PathVariable("bookId") Long bookId) {
        libraryService.deleteBookFromLibrary(libId, bookId);
        return ResponseEntity.ok("The book has been successfully deleted from the library");
    }
}