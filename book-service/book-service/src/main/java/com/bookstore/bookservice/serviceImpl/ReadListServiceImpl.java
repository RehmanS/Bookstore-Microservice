package com.bookstore.bookservice.serviceImpl;

import com.bookstore.bookservice.dto.BookDto;
import com.bookstore.bookservice.entity.ReadList;
import com.bookstore.bookservice.repository.ReadListRepository;
import com.bookstore.bookservice.service.BookService;
import com.bookstore.bookservice.service.ReadListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReadListServiceImpl implements ReadListService {
    private final ReadListRepository readListRepository;
    private final BookService bookService;
    @Override
    public void addBookToReadListByIsbn(String isbn) {
        ReadList readList = new ReadList();
        readList.setIsbn(isbn);
        readListRepository.save(readList);
    }

    @Override
    public List<BookDto> getAllBooksFromReadList() {
        return readListRepository.findAll().stream().map(book -> bookService.getBookByIsbn(book.getIsbn())).toList();
    }

    @Override
    public void deleteFromReadListByIsbn(String isbn) {
        readListRepository.deleteReadListByIsbn(isbn);
    }
}
