package com.acme.ex3.repository;

import com.acme.ex3.model.Book;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Function;

public interface BookRepository  extends JpaRepository<Book, Integer> {

    // this.repository.findById(1, b -> b.getComments());
    default Optional<Book> findById(Integer id, Function<Book, ?>... fetchs){
        if(fetchs.length==0){
            return findById(id);
        }
        else{
            Optional<Book> maybeBook = findById(id);
            if(maybeBook.isPresent()){
                for (Function<Book, ?> fetch : fetchs) {
                    Object lazyRelation = fetch.apply(maybeBook.get());
                    if(lazyRelation!=null){
                        lazyRelation.toString(); // pour déclencher le lazy loading
                    }
                }
            }
            return maybeBook;
        }
    }
}
