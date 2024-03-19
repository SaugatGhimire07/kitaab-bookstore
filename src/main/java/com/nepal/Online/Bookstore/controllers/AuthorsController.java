package com.nepal.Online.Bookstore.controllers;


import com.nepal.Online.Bookstore.models.Author;
import com.nepal.Online.Bookstore.models.AuthorDto;
import com.nepal.Online.Bookstore.services.AuthorsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorsController {
    @Autowired
    private AuthorsRepository repo;
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
        return "redirect:/authors";
    }
}
