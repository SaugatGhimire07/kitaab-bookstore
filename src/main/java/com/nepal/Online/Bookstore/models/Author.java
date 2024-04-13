package com.nepal.Online.Bookstore.models;

import jakarta.persistence.*;
import java.util.Date;

/**
 * Represents an author in the bookstore.
 */
@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorid;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Date createdAt;
    private String authorImageFileName;

    /**
     * Gets the ID of the author.
     * 
     * @return The author ID.
     */
    public Integer getAuthorid() {
        return authorid;
    }

    /**
     * Sets the ID of the author.
     * 
     * @param authorid The author ID to set.
     */
    public void setAuthorid(Integer authorid) {
        this.authorid = authorid;
    }

    /**
     * Gets the name of the author.
     * 
     * @return The author name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the author.
     * 
     * @param name The author name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the author.
     * 
     * @return The author description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the author.
     * 
     * @param description The author description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the creation date of the author.
     * 
     * @return The author creation date.
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date of the author.
     * 
     * @param createdAt The author creation date to set.
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Gets the filename of the author's image.
     * 
     * @return The author image filename.
     */
    public String getAuthorImageFileName() {
        return authorImageFileName;
    }

    /**
     * Sets the filename of the author's image.
     * 
     * @param authorImageFileName The author image filename to set.
     */
    public void setAuthorImageFileName(String authorImageFileName) {
        this.authorImageFileName = authorImageFileName;
    }
}
