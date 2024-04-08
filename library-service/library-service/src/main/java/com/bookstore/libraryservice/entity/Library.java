package com.bookstore.libraryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Library {
    public Library(Long id, String libraryName) {
        this.id = id;
        this.libraryName = libraryName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String libraryName;

    @ElementCollection
    List<Long> userBooks;
}
