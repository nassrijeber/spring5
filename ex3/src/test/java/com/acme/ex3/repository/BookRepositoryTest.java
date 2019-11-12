package com.acme.ex3.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.acme.ex3.ApplicationConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.acme.ex3.model.Author;
import com.acme.ex3.model.Book;

//@SpringJUnitConfig(classes = ApplicationConfig.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-for-tests.properties")
class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Autowired
    private AuthorRepository authorRepository;
	
    @Test
    @Transactional(propagation=Propagation.REQUIRED)
    public void testSave(){
        System.out.println("********************");
        Author author = this.authorRepository.getOne(1); // équivalent de entityManager.getReference(Author.class, 1))
        Book b = new Book();
        b.setTitle("new book");
        b.setAuthor(author);
        this.repository.save(b);
        assertNotNull(b.getId());
        System.out.println("********************");
    }

    @Test
    @Transactional(propagation=Propagation.REQUIRED)
    public void testUpdateExisting(){
        Optional<Book> _book = this.repository.findById(1); // équivalent de Optional.ofNullable(entityManager.find(Book.class, 1))
        if(_book.isPresent()){
            Book book = _book.get();
            book.setTitle("new title");
        }
    }

    @Test
    @Transactional(propagation=Propagation.REQUIRED)
    public void testDelete(){
    	this.repository.deleteById(1);
        assertTrue(this.repository.findById(1).isEmpty());
    }

    @Test
    @Transactional(propagation=Propagation.REQUIRED)
    public void testGetById(){
        Optional<Book> _book1 = this.repository.findById(1); // équivalent de Optional.ofNullable(entityManager.find(Book.class, 1))
        System.out.println("after first call to getById");
        Book book1 = _book1.get();

        Optional<Book> _book1Again = this.repository.findById(1); // équivalent de Optional.ofNullable(entityManager.find(Book.class, 1))
        System.out.println("after second call to getById");
        Book book1Again = _book1Again.get();

        assertTrue(book1==book1Again);
    }

    @Test
    @Transactional(propagation=Propagation.REQUIRED)
    public void testGetByIdWithLazyLoading(){
        Optional<Book> _book = this.repository.findById(1); // équivalent de Optional.ofNullable(entityManager.find(Book.class, 1))
        System.out.println("after call to getById");
        _book.ifPresent(b->{
            System.out.println(b.getTitle());
            System.out.println(b.getAuthor().getFullname());
            System.out.println(b.getComments().size());
        });
    }

    @Test
    @Transactional(propagation=Propagation.REQUIRED)
    public void testGetByIdWithControlledLoading(){
        Optional<Book> _book = this.repository.findById(1, b -> b.getComments());
        System.out.println("after call to getById");
        _book.ifPresent(b -> {
            System.out.println(b.getTitle());
            System.out.println(b.getAuthor().getFullname());
            System.out.println(b.getComments().size());
            System.out.println(b.getReservations().size());
        });
    }


    @Test
    @Transactional(propagation=Propagation.REQUIRED)
    public void testFind() throws Exception{
    	Book probe = new Book();
    	probe.setTitle("e");

    	ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    	Example<Book> example = Example.of(probe, matcher);

        List<Book> results = this.repository.findAll(example);
        for (Book book : results) {
            System.out.println(String.format("%-40s %-40s %-40s", book.getTitle(), book.getAuthor().getFullname(), book.getCategory().getName()));
        }
    }

}

