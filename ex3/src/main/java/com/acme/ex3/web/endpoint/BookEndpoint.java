package com.acme.ex3.web.endpoint;

import com.acme.ex3.model.Book;
import com.acme.ex3.repository.BookRepository;
import com.acme.ex3.web.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookEndpoint {

    @Autowired
    BookRepository bookRepository;

    ExampleMatcher matcher = ExampleMatcher.matching()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
            .withIgnoreCase();

    @RequestMapping(path = "books", method = RequestMethod.GET)
    public List<BookDto> books(@Valid Book book){

  /*      List<Book> bookList = bookRepository.findAll();
        List<BookDto> bookDtoList = new ArrayList<BookDto>();
        for (Book book: bookList) {
            bookDtoList.add(new BookDto(book, true));
        }
        return bookDtoList;*/

        return this.bookRepository.findAll(Example.of(book, matcher)).stream()
                .map(x -> new BookDto(x, false))
                .collect(Collectors.toList());
    }

    @CrossOrigin
    @RequestMapping(path = "searches", method = RequestMethod.POST, params = "domain=books")
    public List<BookDto> search(@Valid @RequestBody Book example){
        return this.bookRepository.findAll(Example.of(example, matcher))
                .stream()
                .map(x -> new BookDto(x, false))
                .collect(Collectors.toList());
    }

    @RequestMapping(path="books/{id}", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public ResponseEntity<BookDto> book(@PathVariable int id){
        Optional<BookDto> bookDto = this.bookRepository.findById(id)
                .map(b -> new BookDto(b, true));
        return ResponseEntity.of(bookDto);
        /*Optional<Book> _book = bookRepository.findById(id);
        if(!_book.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new BookDto(_book.get(), true));*/
    }
}
