package com.acme.ex4.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.query.Query;

import com.acme.ex4.model.Book;

import reactor.core.publisher.Flux;

public interface BookRepository extends R2dbcRepository<Book, Integer> {

	@Query("select id, title from Book where title ilike $1 order by title")
	Flux<Book> find(String title);
}
