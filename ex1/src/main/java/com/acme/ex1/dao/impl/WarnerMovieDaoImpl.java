package com.acme.ex1.dao.impl;

import com.acme.ex1.dao.MovieDao;
import com.acme.ex1.model.Movie;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public class WarnerMovieDaoImpl implements MovieDao {

    private List<Movie> someMovies = List.of(
            new Movie("fellowship of the ring", 2001),
            new Movie("two towers", 2003),
            new Movie("return of the king", 2005)
    );

    @Override
    public Stream<Movie> retrieve(String title) {
        return this.someMovies.stream()
                .filter(m -> m.getTitle().contains(title));
        /*
        return this.someMovies.stream().filter(new Predicate<Movie>() {
            @Override
            public boolean test(Movie movie) {
                return movie.getTitle().contains(title);
            }
        });*/
    }
}
