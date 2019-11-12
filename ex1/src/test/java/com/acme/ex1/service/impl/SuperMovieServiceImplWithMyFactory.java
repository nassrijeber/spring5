package com.acme.ex1.service.impl;

import com.acme.ex1.ApplicationFactory;
import com.acme.ex1.model.Movie;
import com.acme.ex1.service.MovieService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SuperMovieServiceImplWithMyFactory {

    @Test
    void testFind(){
        MovieService service = (MovieService) ApplicationFactory.getBean("superService");
        Stream<Movie> movies = service.find("e");
        List<Movie> list = movies.collect(Collectors.toList());
        for (Movie movie : list) {
            System.out.println(movie.getTitle());
            assertTrue(movie.getTitle().contains("e"));
        }
    }

    public static void main(String[] args) {
        new SuperMovieServiceImplWithMyFactory();
    }
}
