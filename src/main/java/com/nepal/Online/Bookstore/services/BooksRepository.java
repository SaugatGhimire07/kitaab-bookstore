package com.nepal.Online.Bookstore.services;

import com.nepal.Online.Bookstore.models.Author;
import com.nepal.Online.Bookstore.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The BooksRepository interface provides methods to interact with the database
 * for Book entities.
 */
public interface BooksRepository extends JpaRepository<Book, Integer> {

    /**
     * Finds all books written by a specific author.
     *
     * @param author The author to search for.
     * @return A list of books written by the specified author.
     */
    List<Book> findByAuthor(Author author);

    /**
     * Finds all books in descending order of their creation timestamp.
     *
     * @return A list of books sorted by creation timestamp in descending order.
     */
    List<Book> findAllByOrderByCreatedAtDesc();

    /**
     * Finds all books of a specific genre.
     *
     * @param genre The genre to search for.
     * @return A list of books belonging to the specified genre.
     */
    List<Book> findByGenre(String genre);
}
