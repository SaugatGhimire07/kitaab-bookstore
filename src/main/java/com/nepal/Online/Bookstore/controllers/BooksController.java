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

@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BooksRepository repo;

    @Autowired
    private AuthorsRepository authorRepo;

    /**
     * Retrieves and displays the list of books.
     *
     * @param model the model object to add the list of books
     * @return the view name for displaying the list of books
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
     * @param model the model object to add the book DTO and the list of authors
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
     * @param bookDto the book DTO containing the book details
     * @param result  the binding result object for validation errors
     * @param model   the model object to add the list of authors
     * @return the view name for redirecting to the book list page
     */
    @PostMapping("/add")
    public String createBook(
            @Valid @ModelAttribute BookDto bookDto,
            BindingResult result,
            Model model) {
        // Validation for image file
        if (bookDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("bookDto", "imageFile", "The image file is required."));
        }

        if (result.hasErrors()) {
            model.addAttribute("authors", authorRepo.findAll());
            return "books/CreateBook";
        }

        // Save image file
        MultipartFile imageFile = bookDto.getImageFile();
        Date createAt = new Date();
        String storageFileName = createAt.getTime() + "_" + imageFile.getOriginalFilename();

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = imageFile.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setGenre(bookDto.getGenre());
        book.setDescription(bookDto.getDescription());
        book.setPrice(bookDto.getPrice());
        book.setQuantity(bookDto.getQuantity());
        book.setCreatedAt(createAt);
        book.setImageFileName(storageFileName);

        Optional<Author> authorOptional = authorRepo.findById(bookDto.getAuthorId());
        Author author = authorOptional.get();
        book.setAuthor(author);
        System.out.print(book.getAuthor());

        repo.save(book);

        return "redirect:/books";
    }

    /**
     * Displays the edit book form.
     *
     * @param model the model object to add the book, book DTO, and the list of
     *              authors
     * @param id    the ID of the book to edit
     * @return the view name for displaying the edit book form
     */
    @GetMapping("/edit")
    public String showBookEditPage(
            Model model,
            @RequestParam int id) {
        try {
            Book book = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
            model.addAttribute("book", book);

            BookDto bookDto = new BookDto();
            bookDto.setTitle(book.getTitle());
            bookDto.setGenre(book.getGenre());
            bookDto.setQuantity(book.getQuantity());
            bookDto.setPrice(book.getPrice());
            bookDto.setDescription(book.getDescription());
            bookDto.setAuthorId(book.getAuthor().getAuthorid()); // Pass the author ID to the DTO

            model.addAttribute("bookDto", bookDto);

            // Retrieve the list of authors and add it to the model
            List<Author> authors = authorRepo.findAll();
            model.addAttribute("authors", authors);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/books";
        }
        return "books/EditBook";
    }

    /**
     * Updates the book details.
     *
     * @param id      the ID of the book to update
     * @param bookDto the book DTO containing the updated book details
     * @param result  the binding result object for validation errors
     * @return the view name for redirecting to the book list page
     */
    @PostMapping("/edit")
    public String updateBook(
            @RequestParam int id,
            @Valid @ModelAttribute BookDto bookDto,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                return "books/EditBook";
            }

            Book book = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));

            // Update the book details
            book.setTitle(bookDto.getTitle());
            book.setGenre(bookDto.getGenre());
            book.setQuantity(bookDto.getQuantity());
            book.setPrice(bookDto.getPrice());
            book.setDescription(bookDto.getDescription());

            // Update the author of the book
            Optional<Author> authorOptional = authorRepo.findById(bookDto.getAuthorId());
            Author author = authorOptional
                    .orElseThrow(() -> new IllegalArgumentException("Invalid author id: " + bookDto.getAuthorId()));
            book.setAuthor(author);

            if (!bookDto.getImageFile().isEmpty()) {
                // Delete old image
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + book.getImageFileName());
                try {
                    Files.delete(oldImagePath);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

                // Save new image file
                MultipartFile image = bookDto.getImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }

                book.setImageFileName(storageFileName);
            }

            repo.save(book);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/books";
    }

    /**
     * Deletes a book.
     *
     * @param id the ID of the book to delete
     * @return the view name for redirecting to the book list page
     */
    @GetMapping("/delete")
    public String deleteBook(
            @RequestParam int id) {
        try {
            Book book = repo.findById(id).get();
            Path imagePath = Paths.get("products/images/" + book.getImageFileName());

            try {
                Files.delete(imagePath);
            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }

            repo.delete(book);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/books";
    }
}
