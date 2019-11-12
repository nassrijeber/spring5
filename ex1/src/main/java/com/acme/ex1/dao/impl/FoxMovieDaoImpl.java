package com.acme.ex1.dao.impl;

import com.acme.ex1.dao.MovieDao;
import com.acme.ex1.model.Movie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Component
public class FoxMovieDaoImpl implements MovieDao {

    private List<Movie> someMovies = List.of(
            new Movie("a new hope", 1977),
            new Movie("empire strikes back", 1980),
            new Movie("return of the jedi", 1983)
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
