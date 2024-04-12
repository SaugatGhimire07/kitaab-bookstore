package com.nepal.Online.Bookstore.services;

import com.nepal.Online.Bookstore.models.Author;
import com.nepal.Online.Bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByAuthor(Author author);
    List<Book> findAllByOrderByCreatedAtDesc();
    List<Book> findByGenre(String genre);
}
