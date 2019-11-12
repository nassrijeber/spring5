package com.acme.ex4.web.endpoint;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acme.ex4.model.Book;
import com.acme.ex4.repository.BookRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
class BookEndpoint {

	@Autowired
	private final BookRepository repository;
	
	BookEndpoint(BookRepository repository) {
		super();
		this.repository = repository;
	}

	@GetMapping(path = "books/{id}")
	public Mono<ResponseEntity<Book>> getOne(@PathVariable Integer id) {
		Mono<Book> mono = repository.findById(id);
		return mono.map(b -> ResponseEntity.ok(b)).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping(path = "books")
	public Flux<Book> find(@RequestParam String title) {
		return this.repository.find(title);
	}

	@PostMapping(path = "books")
	public Mono<ResponseEntity<Book>> find(@RequestBody Book book) {
		return this.repository.save(book)
				.map(b -> ResponseEntity.created(URI.create("/books/" + b.getId())).body(b));
	}

	@DeleteMapping(path = "books/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable Integer id) {
		return this.repository.findById(id)
				.flatMap(x -> this.repository.delete(x).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
