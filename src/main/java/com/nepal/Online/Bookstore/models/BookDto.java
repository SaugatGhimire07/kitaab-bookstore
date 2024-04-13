package com.nepal.Online.Bookstore.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

/**
 * Represents a book data transfer object (DTO) used for transferring book
 * information.
 */
public class BookDto {
    /**
     * The title of the book.
     */
    @NotEmpty(message = "The book title is required")
    private String title;

    /**
     * The ID of the author of the book.
     */
    @NotNull(message = "The author is required")
    private Integer authorId;

    /**
     * The genre of the book.
     */
    @NotEmpty(message = "The genre is required")
    private String genre;

    /**
     * The quantity of the book.
     */
    @Min(value = 0, message = "The quantity should not be less than zero")
    private int quantity;

    /**
     * The price of the book.
     */
    @Min(0)
    private double price;

    /**
     * The description of the book.
     */
    @Size(min = 10, message = "The description should be at least 10 characters")
    @Size(max = 2000, message = "The description should not exceed 2000 characters")
    private String description;

    /**
     * The image file of the book.
     */
    private MultipartFile imageFile;

    /**
     * Get the title of the book.
     *
     * @return The title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the book.
     *
     * @param title The title of the book.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the ID of the author of the book.
     *
     * @return The ID of the author of the book.
     */
    public Integer getAuthorId() {
        return authorId;
    }

    /**
     * Set the ID of the author of the book.
     *
     * @param authorId The ID of the author of the book.
     */
    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    /**
     * Get the genre of the book.
     *
     * @return The genre of the book.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Set the genre of the book.
     *
     * @param genre The genre of the book.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Get the quantity of the book.
     *
     * @return The quantity of the book.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity of the book.
     *
     * @param quantity The quantity of the book.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get the price of the book.
     *
     * @return The price of the book.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set the price of the book.
     *
     * @param price The price of the book.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the description of the book.
     *
     * @return The description of the book.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the book.
     *
     * @param description The description of the book.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the image file of the book.
     *
     * @return The image file of the book.
     */
    public MultipartFile getImageFile() {
        return imageFile;
    }

    /**
     * Set the image file of the book.
     *
     * @param imageFile The image file of the book.
     */
    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
