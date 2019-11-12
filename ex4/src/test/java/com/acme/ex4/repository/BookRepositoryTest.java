package com.acme.ex4.repository;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.acme.ex4.MyApplication;
import com.acme.ex4.model.Book;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(classes=MyApplication.class)
class BookRepositoryTest {
    
    @Autowired
    private BookRepository repository;
	
	
	private int aFewMilliSeconds = 2500;
	
	/* 
	https://spring.io/blog/2019/03/06/flight-of-the-flux-1-assembly-vs-subscription
	
	extraits :

		Nothing Happens Until You Subscribe
	
		By default, Reactor’s reactive types are lazy.
		
		Calling methods on a Flux or Mono (the operators) doesn’t immediately trigger the behavior. 
		Instead, a new instance of Flux (or Mono) is returned, on which you can continue composing further operators. 
		You thus create a chain of operators (or an operator acyclic graph), which represents your asynchronous processing pipeline.

		Compare that with a CompletableFuture, which is not lazy in nature: 
		once you have a reference to the CompletableFuture, it means the processing is already ongoing…
	 */
    @Test
    void tryGetById() throws Exception{
    	// log shows that sql query is run only because there is a subscription.
    	System.out.println("before call to repository");
        Mono<Book> mono = this.repository.findById(1);
        System.out.println("after call to repository");
    	mono.subscribe(x -> System.out.println(x.getTitle()));
        Thread.sleep(aFewMilliSeconds);
    }    
    
    @Test
    void tryGetByIdWithIntermediateAction() throws Exception{
    	System.out.println("before call to repository");
        this.repository.findById(1)
        		.doOnNext((Book b) -> System.out.println("book "+b.getId()+" has been retrieved"))
        	.map(book -> book.getTitle())
        	.subscribe((String title) -> System.out.println(title));
        System.out.println("after call to repository");
        Thread.sleep(aFewMilliSeconds);
    }    
    
    @Test
    void tryGetByIdNotFound() throws Exception{
    	System.out.println("before call to repository");
        this.repository.findById(999)
        		.map((Book b) -> b.getTitle())
        		.defaultIfEmpty("empty !!")
        		.subscribe((String s) -> System.out.println(s));
        System.out.println("after call to repository");
        Thread.sleep(aFewMilliSeconds);
    }
    
    @Test
    void trySave() throws InterruptedException{
        Book b = new Book();
        b.setTitle("new book");
        System.out.println("before call to repository");
        this.repository.save(b)
        	.subscribe(x -> System.out.println(x.getId()));
        System.out.println("after call to repository");
        Thread.sleep(aFewMilliSeconds);
    }
    
    @Test
    void tryUpdateExisting() throws Exception{
    	System.out.println("before call to repository");
        this.repository.findById(1)
        	.flatMap((Book b) -> {
        		b.setTitle("Walden"+ " "+LocalTime.now());
        		return this.repository.save(b);
        	})
        	.subscribe((Book b) -> System.out.println(b.getTitle()+" has been updated"));
        System.out.println("after call to repository");
        Thread.sleep(aFewMilliSeconds);
    }

    @Test
    void tryDelete() throws Exception{
        System.out.println("before call to repository");

        Book b = new Book();
        b.setTitle("new book");
        this.repository.save(b).flatMap((Book savedBook)-> {
        		System.out.println(savedBook.toString());
        		return this.repository.delete(savedBook);
        }).doOnSuccess((Void nothing) -> System.out.println("deleted !!"))
        .subscribe();
        System.out.println("after call to repository");
        Thread.sleep(aFewMilliSeconds);
    }

    @Test
    void tryCount() throws Exception{
    	System.out.println("before call to repository");
    	this.repository.count()
    		.subscribe((Long count) -> System.out.println(count));
    	System.out.println("afer call to repository");

    	Thread.sleep(aFewMilliSeconds);
    }
    
    @Test
    void tryFind() throws Exception{
    	System.out.println("before call to repository");
    	this.repository.find("%a%").subscribe((Book b) -> System.out.println(b.getTitle()));
    	System.out.println("after call to repository");
		
    	Thread.sleep(aFewMilliSeconds);
    }
    
    

    @Test
    void testSave() throws InterruptedException{
        Book b = new Book("new book");
        System.out.println("before call to repository");
        Mono<Book> mono = this.repository.save(b);
        System.out.println("after call to repository");
        
        StepVerifier.create(mono)
        	.expectNextMatches(savedBook -> savedBook.getId() != null)
        	.verifyComplete();
    }
    
    @Test
    void testGetById() throws Exception{
    	System.out.println("before call to repository");
    	Mono<Book> mono = this.repository.findById(1);
    	System.out.println("after call to repository");
    	
    	StepVerifier.create(mono)
    		.expectNextMatches(x -> x.getTitle().contains("Walden"))
    		.verifyComplete();
    }
    
    @Test
    void testGetByIdNotFound() throws Exception{
    	System.out.println("before call to repository");
        Mono<Book> mono = this.repository.findById(999);
        System.out.println("after call to repository");

    	StepVerifier.create(mono).expectNextCount(0).verifyComplete();
    }  

    @Test
    void testCount(){
    	System.out.println("before call to repository");
    	Mono<Long> mono = this.repository.count();
        System.out.println("after call to repository");
    	
    	StepVerifier.create(mono)
    		.expectNextMatches(n -> n > 0)
    		.verifyComplete();
    }

    @Test
    void testFind() throws Exception{
    	System.out.println("before call to repository");
    	Flux<Book> flux = this.repository.find("%a%");
    	System.out.println("after call to repository");
    	StepVerifier.create(flux)
    		.expectNextMatches(x->x.getTitle().toLowerCase().contains("a"))
    		.expectComplete();
    	}    
}

