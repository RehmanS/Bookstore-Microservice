package com.bookstore.bookservice.repository;

import com.bookstore.bookservice.entity.ReadList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadListRepository extends JpaRepository<ReadList,String> {
    void deleteReadListByIsbn(String isbn);
}
