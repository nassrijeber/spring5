package com.acme.ex1.service.impl;

import com.acme.ex1.dao.MovieDao;
import com.acme.ex1.model.Movie;
import com.acme.ex1.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Stream;

@Service
public class SuperMovieServiceImpl implements MovieService {

    private final Set<MovieDao> daos;

    public SuperMovieServiceImpl(Set<MovieDao> daos) {
        this.daos = daos;
        for (MovieDao dao : this.daos) {
            System.out.println("I will be working with "+dao.getClass());
        }
    }

    @Override
    public Stream<Movie> find(String title) {
        return this.daos.stream()
                .flatMap(d -> d.retrieve(title));
    }
}
