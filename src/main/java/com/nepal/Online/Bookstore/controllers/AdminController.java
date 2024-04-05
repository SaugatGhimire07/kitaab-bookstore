package com.nepal.Online.Bookstore.controllers;

import com.nepal.Online.Bookstore.models.Book;
import com.nepal.Online.Bookstore.services.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private BooksRepository repo;

    @GetMapping("/")
    public String indexPage(Model model) {
        List<Book> books = repo.findAll();
        model.addAttribute("books", books);
        return "index";
    }
    @GetMapping("/admin")
    public String showAdminPage() {
        return "admin";
    }
}
