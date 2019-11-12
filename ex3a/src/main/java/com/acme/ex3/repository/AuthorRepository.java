package com.acme.ex3.repository;

import com.acme.ex3.model.Author;
import com.acme.ex3.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
