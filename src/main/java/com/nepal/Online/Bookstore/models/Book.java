package com.nepal.Online.Bookstore.models;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Represents a book in the bookstore.
 */
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookid;

    private String title;

    // Define the author as a many-to-one relationship
    @ManyToOne
    @JoinColumn(name = "authorid", referencedColumnName = "authorid")
    private Author author;

    private String genre;
    private double price;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Date createdAt;
    private String imageFileName;
    private int quantity;

    /**
     * Gets the ID of the book.
     * 
     * @return The book ID.
     */
    public int getBookid() {
        return bookid;
    }

    /**
     * Sets the ID of the book.
     * 
     * @param bookid The book ID to set.
     */
    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    /**
     * Gets the title of the book.
     * 
     * @return The book title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the book.
     * 
     * @param title The book title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author of the book.
     * 
     * @return The book author.
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     * 
     * @param author The book author to set.
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Gets the genre of the book.
     * 
     * @return The book genre.
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets the genre of the book.
     * 
     * @param genre The book genre to set.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Gets the quantity of the book.
     * 
     * @return The book quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the book.
     * 
     * @param quantity The book quantity to set.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the price of the book.
     * 
     * @return The book price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the book.
     * 
     * @param price The book price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the description of the book.
     * 
     * @return The book description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the book.
     * 
     * @param description The book description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the creation date of the book.
     * 
     * @return The book creation date.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date of the book.
     * 
     * @param createdAt The book creation date to set.
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the image file name of the book.
     * 
     * @return The book image file name.
     */
    public String getImageFileName() {
        return imageFileName;
    }

    /**
     * Sets the image file name of the book.
     * 
     * @param imageFileName The book image file name to set.
     */
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
