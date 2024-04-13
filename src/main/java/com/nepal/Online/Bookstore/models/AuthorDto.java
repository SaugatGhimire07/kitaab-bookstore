package com.nepal.Online.Bookstore.models;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 * Represents an Author Data Transfer Object (DTO).
 * This class is used to transfer author data between different layers of the
 * application.
 */
public class AuthorDto {
    /**
     * The name of the author.
     */
    @NotEmpty(message = "The author name is required")
    private String name;

    /**
     * The description of the author.
     */
    @NotEmpty(message = "The author description is required")
    private String description;

    /**
     * The image file of the author.
     */
    private MultipartFile authorImageFile;

    /**
     * Get the name of the author.
     *
     * @return The name of the author.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the author.
     *
     * @param name The name of the author.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description of the author.
     *
     * @return The description of the author.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the author.
     *
     * @param description The description of the author.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the image file of the author.
     *
     * @return The image file of the author.
     */
    public MultipartFile getAuthorImageFile() {
        return authorImageFile;
    }

    /**
     * Set the image file of the author.
     *
     * @param authorImageFile The image file of the author.
     */
    public void setAuthorImageFile(MultipartFile authorImageFile) {
        this.authorImageFile = authorImageFile;
    }
}
