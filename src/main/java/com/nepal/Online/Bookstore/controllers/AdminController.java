package com.nepal.Online.Bookstore.controllers;

import com.nepal.Online.Bookstore.models.Author;
import com.nepal.Online.Bookstore.models.Book;
import com.nepal.Online.Bookstore.services.AuthorsRepository;
import com.nepal.Online.Bookstore.services.BooksRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The AdminController class handles the requests and controls the flow of the
 * admin-related pages in the bookstore application.
 */
@Controller
public class AdminController {
    @Autowired
    private BooksRepository repo;

    @Autowired
    private AuthorsRepository authrepo;

    @Autowired
    private HttpSession session;

    /**
     * Handles the request for the index page.
     * Fetches all books, filters fiction books, and fetches authors to add them to
     * the model.
     * 
     * @param model the model object to add attributes to
     * @return the name of the view template to render
     */
    @GetMapping("/")
    public String indexPage(Model model) {
        // Fetch all books and add them to the model
        List<Book> allBooks = repo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("books", allBooks);

        // Filter fiction books and add them to the model
        List<Book> fictionBooks = allBooks.stream()
                .filter(book -> "Fiction".equals(book.getGenre()))
                .limit(4) // Limit to maximum 4 books
                .collect(Collectors.toList());
        model.addAttribute("fictionBooks", fictionBooks);

        // Fetch authors and add them to the model
        List<Author> authors = authrepo.findAll();
        model.addAttribute("authors", authors);

        return "index";
    }

    /**
     * Handles the request for the admin page.
     * 
     * @return the name of the view template to render
     */
    @GetMapping("/admin")
    public String showAdminPage() {
        return "admin";
    }

    /**
     * Handles the request for the all books page.
     * Fetches all books and adds them to the model.
     * 
     * @param model the model object to add attributes to
     * @return the name of the view template to render
     */
    @GetMapping("/allbooks")
    public String showAllBooksPage(Model model) {
        List<Book> books = repo.findAll();
        model.addAttribute("books", books);
        return "allbooks";
    }

    /**
     * Handles the request for the new arrivals page.
     * Fetches all books and adds them to the model.
     * 
     * @param model the model object to add attributes to
     * @return the name of the view template to render
     */
    @GetMapping("/newarrivals")
    public String showNewArrivalsPage(Model model) {
        List<Book> allBooks = repo.findAllByOrderByCreatedAtDesc();
        model.addAttribute("books", allBooks);
        return "newarrivals";
    }

    /**
     * Handles the request for the book details page.
     * Retrieves the book details from the repository based on the provided ID.
     * Adds the book details, author, and other books by the same author to the
     * model.
     * 
     * @param model the model object to add attributes to
     * @param id    the ID of the book
     * @return the name of the view template to render
     */
    @GetMapping("/bookdetails")
    public String showBookDetailsPage(
            Model model,
            @RequestParam int id) {
        // Retrieve the book details from the repository based on the provided ID
        Book book = repo.findById(id).orElse(null);

        // Check if the book exists
        if (book != null) {
            // Add the book details to the model
            model.addAttribute("book", book);

            // Retrieve the author of the book
            Author author = book.getAuthor();

            // Retrieve all books by the same author
            List<Book> booksByAuthor = repo.findByAuthor(author);

            // Remove the current book from the list of books by the same author
            booksByAuthor.remove(book);

            // Add the list of books by the same author to the model
            model.addAttribute("booksByAuthor", booksByAuthor);

            return "bookdetails";
        } else {
            // If the book with the provided ID does not exist, redirect to an error page or
            // handle it appropriately
            return "error"; // Return the error.html template, for example
        }
    }

    /**
     * Handles the request for the author details page.
     * Retrieves the author details from the repository based on the provided ID.
     * Adds the author and books by the author to the model.
     * 
     * @param model the model object to add attributes to
     * @param id    the ID of the author
     * @return the name of the view template to render
     */
    @GetMapping("/authordetails")
    public String showAuthorDetailsPage(
            Model model,
            @RequestParam int id) {
        // Retrieve the author details from the repository based on the provided ID
        Author author = authrepo.findById(id).orElse(null);

        // Check if the author exists
        if (author != null) {
            // Retrieve all books by the author using the custom query method
            List<Book> booksByAuthor = repo.findByAuthor(author);

            // Add the author and books to the model
            model.addAttribute("author", author);
            model.addAttribute("booksByAuthor", booksByAuthor);

            return "authordetails"; // Return the authordetails.html template
        } else {
            // If the author with the provided ID does not exist, handle it appropriately
            return "error"; // Return the error.html template, for example
        }
    }

    /**
     * Handles the request for the cart page.
     * 
     * @return the name of the view template to render
     */
    @GetMapping("/cart")
    public String showCartPage() {
        return "cart";
    }

    /**
     * Handles the request for the find us page.
     * 
     * @return the name of the view template to render
     */
    @GetMapping("/findus")
    public String showFindUsPage() {
        return "findus";
    }

    /**
     * Handles the request for the great country page.
     * 
     * @return the name of the view template to render
     */
    @GetMapping("/greatcountry")
    public String showGreatCountryPage() {
        return "great";
    }
}
