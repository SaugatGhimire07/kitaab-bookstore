package com.nepal.Online.Bookstore.controllers;


import com.nepal.Online.Bookstore.models.Author;
import com.nepal.Online.Bookstore.models.AuthorDto;
import com.nepal.Online.Bookstore.models.Book;
import com.nepal.Online.Bookstore.services.AuthorsRepository;
import com.nepal.Online.Bookstore.services.BooksRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/authors")
public class AuthorsController {
    @Autowired
    private AuthorsRepository repo;
    @Autowired
    private BooksRepository booksRepository;

    @GetMapping("")
    public String showAuthorList(Model model) {
        List<Author> authors = repo.findAll();
        model.addAttribute("authors", authors);
        return "authors/author";
    }

    @GetMapping("/add")
    public String showCreateAuthPage(Model model) {
        AuthorDto authorDto = new AuthorDto();
        model.addAttribute("authorDto", authorDto);
        return "authors/CreateAuthor";
    }

    @PostMapping("/add")
    public String createAuthor(
            @Valid @ModelAttribute AuthorDto authorDto,
            BindingResult result
    ) {

        if(authorDto.getAuthorImageFile().isEmpty()) {
            result.addError(new FieldError("authorDto", "authorImageFile", "The image file is required."));
        }

        if (result.hasErrors()) {
            return "authors/CreateAuthor";
        }

        //save image file
        MultipartFile authorImage = authorDto.getAuthorImageFile();
        Date createAt = new Date();
        String storageFileName = createAt.getTime() + "_" + authorImage.getOriginalFilename();

        try{
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = authorImage.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (Exception ex) {
            System.out.println("Execption: " + ex.getMessage());
        }

        Author author = new Author();
        author.setName(authorDto.getName());
        author.setCreatedAt(createAt);
        author.setAuthorImageFileName(storageFileName);

        repo.save(author);

        return "redirect:/authors";
    }

    @GetMapping("/edit")
    public String showAuthEditPage(
            Model model,
            @RequestParam int id
    ){
        try{
            Author author = repo.findById(id).get();
            model.addAttribute("author", author);

            AuthorDto authorDto = new AuthorDto();
            authorDto.setName(author.getName());

            model.addAttribute("authorDto", authorDto);
        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/authors";
        }
        return "authors/EditAuthor";
    }

    @PostMapping ("/edit")
    public String updateAuthor(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute AuthorDto authorDto,
            BindingResult result
    ) {
        try{
            Author author = repo.findById(id).get();
            model.addAttribute("author", author);

            if (result.hasErrors()){
                return "authors/EditAuthor";
            }

            if (!authorDto.getAuthorImageFile().isEmpty()) {
            // delete old image
                String uploadDir = "public/images/";
                Path oldImagePath = Paths.get(uploadDir + author.getAuthorImageFileName());
                try {
                    Files.delete(oldImagePath);
                }
                catch (Exception ex) {
                    System.out.println("Exception: " +ex.getMessage());
                }

                // save new image file
                MultipartFile image = authorDto.getAuthorImageFile();
                Date createdAt = new Date();
                String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy (inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
                }

                author.setAuthorImageFileName (storageFileName);
            }
            author.setName(authorDto.getName());

            repo.save(author);
        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/authors";
    }

    @GetMapping ("/delete")
    public String deleteAuthor(
            @RequestParam int id,
            RedirectAttributes redirectAttributes
    ){
        try{
            Author author = repo.findById(id).get();

            List<Book> booksByAuthor = booksRepository.findByAuthor(author);

            if (!booksByAuthor.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Sorry, Author is referenced by at least one book. Please remove the associated books first, and then try again.");
            }

            Path imagePath = Paths.get("products/images/" + author.getAuthorImageFileName());

            try{
                Files.delete(imagePath);
            }
            catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }

            repo.delete(author);
        }
        catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/authors";
    }
}
