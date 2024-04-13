package com.nepal.Online.Bookstore.controllers;

import com.nepal.Online.Bookstore.models.Author;
import com.nepal.Online.Bookstore.models.AuthorDto;
import com.nepal.Online.Bookstore.models.Book;
import com.nepal.Online.Bookstore.models.BookDto;
import com.nepal.Online.Bookstore.services.AuthorsRepository;
import com.nepal.Online.Bookstore.services.BooksRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Controller class for managing books in the bookstore.
 */
@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BooksRepository repo;

    @Autowired
    private AuthorsRepository authorRepo;

    /**
     * Retrieves the list of books and displays them.
     *
     * @param model the model object to add attributes to
     * @return the view name for displaying the book list
     */
    @GetMapping("")
    public String showBookList(Model model) {
        List<Book> books = repo.findAll();
        model.addAttribute("books", books);
        return "books/book";
    }

    /**
     * Displays the create book form.
     *
     * @param model the model object to add attributes to
     * @return the view name for displaying the create book form
     */
    @GetMapping("/add")
    public String showCreatePage(Model model) {
        BookDto bookDto = new BookDto();
        model.addAttribute("bookDto", bookDto);
        model.addAttribute("authors", authorRepo.findAll());
        return "books/CreateBook";
    }

    /**
     * Creates a new book.
     *
     * @param bookDto the book data transfer object
     * @param result  the binding result for validation errors
     * @param model   the model object to add attributes to
     * @return the view name for redirecting to the book list
     */
    @PostMapping("/add")
    public String createBook(
            @Valid @ModelAttribute BookDto bookDto,
            BindingResult result,
            Model model) {
        // Code for creating a book
        // ...

        return "redirect:/books";
    }

    /**
     * Displays the edit book form.
     *
     * @param model the model object to add attributes to
     * @param id    the ID of the book to edit
     * @return the view name for displaying the edit book form
     */
    @GetMapping("/edit")
    public String showBookEditPage(
            Model model,
            @RequestParam int id) {
        // Code for displaying the edit book form
        // ...

        return "books/EditBook";
    }

    /**
     * Updates an existing book.
     *
     * @param id      the ID of the book to update
     * @param bookDto the book data transfer object
     * @param result  the binding result for validation errors
     * @return the view name for redirecting to the book list
     */
    @PostMapping("/edit")
    public String updateBook(
            @RequestParam int id,
            @Valid @ModelAttribute BookDto bookDto,
            BindingResult result) {
        // Code for updating the book
        // ...

        return "redirect:/books";
    }

    /**
     * Deletes a book.
     *
     * @param id the ID of the book to delete
     * @return the view name for redirecting to the book list
     */
    @GetMapping("/delete")
    public String deleteBook(
            @RequestParam int id) {
        // Code for deleting the book
        // ...

        return "redirect:/books";
    }
}
