package com.nepal.Online.Bookstore.controllers;

import com.nepal.Online.Bookstore.models.Book;
import com.nepal.Online.Bookstore.services.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/category")
public class CategoriesController {
    @Autowired
    private BooksRepository repo;

    @GetMapping
    public String showCategoriesPage(Model model) {
        // Retrieve all books from the repository
        List<Book> allBooks = repo.findAll();

        // Extract genres from the books
        Set<String> genres = allBooks.stream()
                .map(Book::getGenre)
                .collect(Collectors.toSet());

        // Add genres to the model
        model.addAttribute("genres", genres);

        // Return the correct template name with the path
        return "categories/category";
    }

    @GetMapping("/{genre}")
    public String showBooksByGenre(@PathVariable String genre, Model model) {
        // Retrieve books with the specified genre from the repository
        List<Book> booksByGenre = repo.findByGenre(genre);

        // Add the books to the model
        model.addAttribute("books", booksByGenre);

        // Add the genre to the model for display purposes
        model.addAttribute("genre", genre);

        return "categories/genrebooks";
    }
}


