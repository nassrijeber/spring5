package com.acme.ex3.web.controller;

import com.acme.ex3.model.Author;
import com.acme.ex3.model.Book;
import com.acme.ex3.model.Reservation;
import com.acme.ex3.repository.BookRepository;
import com.acme.ex3.repository.ReservationRepository;
import com.acme.ex3.service.command.ReservationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping("books")
    public String books(Map<String, Object> model){
        model.put("book", new Book());
        return "books/list";
    }


    /*@PostMapping("books")
    public String book(Book book, Map<String, Object> model){
//        Book = = new Book();
//        Author author = new Author();
//        book.setTitle(title);
//        author.setLastname(authorName);
//        book.setAuthor(author);
//
//        bookRepository.save(book);
//        Map<String, Object> model =  new HashMap<String, Object>();
//        List<Book> bookList = bookRepository.findAll();
//        model.put("result", bookList);
//        return "books/list";
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        List<Book> results = bookRepository.findAll(Example.of(book, matcher));
        model.put("results", results);
        return "books/list";
    }*/

   /* @GetMapping(path="books", params = "title")
    public String books(@Valid Book book, Map<String, Object> model, BindingResult binding){
//        Book = = new Book();
//        Author author = new Author();
//        book.setTitle(title);
//        author.setLastname(authorName);
//        book.setAuthor(author);
//
//        bookRepository.save(book);
//        Map<String, Object> model =  new HashMap<String, Object>();
//        List<Book> bookList = bookRepository.findAll();
//        model.put("result", bookList);
//        return "books/list";
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        if (binding.hasErrors()){
            return "books/list";
        }
        List<Book> results = bookRepository.findAll(Example.of(book, matcher));
        model.put("results", results);
        return "books/list";
    }*/

    @GetMapping(path="books", params = "title")
    public String books(@Valid Book book, BindingResult br, Map<String, Object> model) {
        if(br.hasErrors()){
            return "books/list";
        }
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        List<Book> results = this.bookRepository.findAll(Example.of(book, matcher));
        model.put("results", results);
        return "books/list";
    }

    @GetMapping("books/{id}")
    public String book(@PathVariable int id, Map<String, Object> model){
        Optional<Book> maybeBook = this.bookRepository.findById(id);
        maybeBook.ifPresent(book -> model.put("entity", book));
        model.put("reservationCommand", new ReservationCommand());
        return "/books/detail";
    }

    @PostMapping("reservations")
    public String reservation(ReservationCommand command){
//        this.reservationRepository.save(reservation);
        return "/books";
    }
}
