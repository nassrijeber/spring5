package com.acme.ex1.service.impl;

import com.acme.ex1.dao.MovieDao;
import com.acme.ex1.model.Movie;
import com.acme.ex1.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

public class MovieServiceImpl implements MovieService {

    // j'ai besoin de FoxMovieDaoImpl
    // j'ai besoin de WarnerMovieDaoImpl
    // j'ai besoin d'une MovieDao
    private final MovieDao dao;

    public MovieServiceImpl(MovieDao dao) {
        this.dao = dao;
    }

    @Override
    public Stream<Movie> find(String title) {
        return this.dao.retrieve(title);
    }
}
