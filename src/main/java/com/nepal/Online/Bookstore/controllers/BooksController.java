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

    @GetMapping("")
    public String showBookList(Model model) {
        List<Book> books = repo.findAll();
        model.addAttribute("books", books);
        return "books/book";
    }
    @GetMapping("/add")
    public String showCreatePage(Model model) {
        BookDto bookDto = new BookDto();
        model.addAttribute("bookDto", bookDto);
        model.addAttribute("authors", authorRepo.findAll());
        return "books/CreateBook";
    }

    @PostMapping("/add")
    public String createBook(
            @Valid @ModelAttribute BookDto bookDto,
            BindingResult result,
            Model model
    ) {

        if(bookDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("bookDto", "imageFile", "The image file is required."));
        }

        if (result.hasErrors()) {
            model.addAttribute("authors", authorRepo.findAll());
            return "books/CreateBook";
        }


        //save image file
        MultipartFile imageFile = bookDto.getImageFile();
        Date createAt = new Date();
        String storageFileName = createAt.getTime() + "_" + imageFile.getOriginalFilename();

        try{
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = imageFile.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (Exception ex) {
            System.out.println("Execption: " + ex.getMessage());
        }

        Book book = new Book();
        book.setTitle(bookDto.getTitle());
        book.setGenre(bookDto.getGenre());
        book.setDescription(bookDto.getDescription());
        book.setPrice(bookDto.getPrice());
        book.setCreatedAt(createAt);
        book.setImageFileName(storageFileName);

        Optional<Author> authorOptional = authorRepo.findById(bookDto.getAuthorId());
        Author author = authorOptional.get();
        book.setAuthor(author);
        System.out.print(book.getAuthor());

        repo.save(book);

        return "redirect:/books";
    }

    @GetMapping("/edit")
    public String showBookEditPage(
            Model model,
            @RequestParam int id
    ){
        try{
            Book book = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));
            model.addAttribute("book", book);

            BookDto bookDto = new BookDto();
            bookDto.setTitle(book.getTitle());
            bookDto.setGenre(book.getGenre());
            bookDto.setPrice(book.getPrice());
            bookDto.setDescription(book.getDescription());
            bookDto.setAuthorId(book.getAuthor().getAuthorid()); // Pass the author ID to the DTO

            model.addAttribute("bookDto", bookDto);

            // Retrieve the list of authors and add it to the model
            List<Author> authors = authorRepo.findAll();
            model.addAttribute("authors", authors);
        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/books";
        }
        return "books/EditBook";
    }

    @PostMapping("/edit")
    public String updateBook(
            @RequestParam int id,
            @Valid @ModelAttribute BookDto bookDto,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                return "books/EditBook";
            }

            Book book = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + id));

            // Update the book details
            book.setTitle(bookDto.getTitle());
            book.setGenre(bookDto.getGenre());
            book.setPrice(bookDto.getPrice());
            book.setDescription(bookDto.getDescription());

            // Update the author of the book
            Optional<Author> authorOptional = authorRepo.findById(bookDto.getAuthorId());
            Author author = authorOptional.orElseThrow(() -> new IllegalArgumentException("Invalid author id: " + bookDto.getAuthorId()));
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
                    Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
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


    @GetMapping ("/delete")
    public String deleteBook(
            @RequestParam int id
    ){
        try{
            Book book = repo.findById(id).get();
            Path imagePath = Paths.get("products/images/" + book.getImageFileName());

            try{
                Files.delete(imagePath);
            }
            catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }

            repo.delete(book);
        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/books";
    }
}
