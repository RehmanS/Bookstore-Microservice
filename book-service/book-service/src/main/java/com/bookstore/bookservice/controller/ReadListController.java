package com.bookstore.bookservice.controller;

import com.bookstore.bookservice.dto.BookDto;
import com.bookstore.bookservice.service.ReadListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/readList")
@RequiredArgsConstructor
public class ReadListController {
    private final ReadListService readListService;

    @GetMapping("/add/{isbn}")
    public void addBookToReadListByIsbn(@PathVariable("isbn") String isbn) {
        readListService.addBookToReadListByIsbn(isbn);
    }

    @GetMapping("/get")
    public List<BookDto> getAllBooksFromReadList() {
        return readListService.getAllBooksFromReadList();
    }
}
