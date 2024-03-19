package com.nepal.Online.Bookstore.services;

import com.nepal.Online.Bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository<Book, Integer> {
}
