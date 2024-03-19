package com.nepal.Online.Bookstore.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int authorid;

    private String name;

    private Date createdAt;
    private String authorImageFileName;

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
