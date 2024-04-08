package com.bookstore.libraryservice.repository;

import com.bookstore.libraryservice.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library,Long> {
}
