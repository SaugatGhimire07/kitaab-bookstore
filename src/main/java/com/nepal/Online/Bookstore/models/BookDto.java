package com.nepal.Online.Bookstore.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class BookDto {
    @NotEmpty (message = "The book title is required")
    private String title;

    @NotEmpty (message = "The author name is required")
    private Author author;

    @NotEmpty (message = "The genre is required")
    private String genre;
    @Min(0)
    private double price;

    @Size(min = 10, message = "The description should be at least 10 characters")
    @Size(max = 2000, message = "The description should not exceed 2000 characters")
    private String description;
    private MultipartFile imageFile;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
