package com.acme.ex1.service;

import com.acme.ex1.model.Movie;

import java.util.stream.Stream;

public interface MovieService {
    Stream<Movie> find(String title);
}
