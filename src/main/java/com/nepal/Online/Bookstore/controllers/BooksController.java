package com.nepal.Online.Bookstore.controllers;

import com.nepal.Online.Bookstore.models.Book;
import com.nepal.Online.Bookstore.models.BookDto;
import com.nepal.Online.Bookstore.services.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BooksRepository repo;

    @GetMapping("")
    public String showBookList(Model model) {
        List<Book> books = repo.findAll();
        model.addAttribute("books", books);
        return "books/index";
    }
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        BookDto bookDto = new BookDto();
        model.addAttribute("bookDto", bookDto);
        return "books/CreateBook";
    }
}
