package com.nepal.Online.Bookstore.models;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class AuthorDto {
    @NotEmpty(message = "The author name is required")
    private String name;

    @NotEmpty(message = "The author description is required")
    private String description;

    private MultipartFile authorImageFile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getAuthorImageFile() {
        return authorImageFile;
    }

    public void setAuthorImageFile(MultipartFile authorImageFile) {
        this.authorImageFile = authorImageFile;
    }
}
