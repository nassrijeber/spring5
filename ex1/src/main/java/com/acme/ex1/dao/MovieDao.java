package com.acme.ex1.dao;

import com.acme.ex1.model.Movie;

import java.util.stream.Stream;

public interface MovieDao {

    Stream<Movie> retrieve(String title);
}
