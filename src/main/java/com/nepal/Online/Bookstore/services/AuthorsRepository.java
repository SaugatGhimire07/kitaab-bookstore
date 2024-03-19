package com.nepal.Online.Bookstore.services;

import com.nepal.Online.Bookstore.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorsRepository extends JpaRepository <Author, Integer> {
}
