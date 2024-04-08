package com.bookstore.bookservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class ReadList {
    @Id
    String isbn;
}
