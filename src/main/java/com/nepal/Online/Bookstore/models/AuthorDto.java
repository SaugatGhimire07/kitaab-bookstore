package com.nepal.Online.Bookstore.models;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public class AuthorDto {
    @NotEmpty (message = "The author name is required")
    private String name;
    private MultipartFile authorImageFile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getAuthorImageFile() {
        return authorImageFile;
    }

    public void setAuthorImageFile(MultipartFile authorImageFile) {
        this.authorImageFile = authorImageFile;
    }
}
