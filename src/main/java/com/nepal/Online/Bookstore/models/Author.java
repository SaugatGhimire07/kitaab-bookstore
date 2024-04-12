package com.nepal.Online.Bookstore.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer authorid;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Date createdAt;
    private String authorImageFileName;

    public Integer getAuthorid() {
        return authorid;
    }

    public void setAuthorid(Integer authorid) {
        this.authorid = authorid;
    }

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAuthorImageFileName() {
        return authorImageFileName;
    }

    public void setAuthorImageFileName(String authorImageFileName) {
        this.authorImageFileName = authorImageFileName;
    }
}
