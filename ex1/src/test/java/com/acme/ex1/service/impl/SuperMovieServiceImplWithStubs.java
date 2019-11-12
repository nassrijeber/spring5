package com.acme.ex1.service.impl;

import com.acme.ex1.dao.MovieDao;
import com.acme.ex1.model.Movie;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SuperMovieServiceImplWithStubs {

    @Test
    void testFind(){
        MovieDao dao1 = s -> Stream.of(new Movie("movie 1", 0));
        MovieDao dao2 = s -> Stream.of(new Movie("movie 2", 0));
        SuperMovieServiceImpl service = new SuperMovieServiceImpl(Set.of(dao1, dao2));
        assertEquals(2, service.find("e").count());
    }
}
