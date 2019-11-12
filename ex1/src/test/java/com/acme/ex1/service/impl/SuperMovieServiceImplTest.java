package com.acme.ex1.service.impl;

import com.acme.ex1.ApplicationConfig;
import com.acme.ex1.service.MovieService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.Closeable;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SuperMovieServiceImplTest {

    private static MovieService service;

    private static ApplicationContext ctx;
    
    @BeforeAll
    static void setup() {
        // TODO : valorisation du champ ctx
    	ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
    	// TODO : valorisation du champ service
        service = ctx.getBean(SuperMovieServiceImpl.class);
    }

    @Test
    void testFindMoviesWithResults() {
        service.find("e").forEach(m -> {
            System.out.println(m.getTitle());
            assertTrue(m.getTitle().contains("e"));
        });
    }

    @Test
    void testFindMoviesWithNoResult() {
        assertNotNull(service.find("_*_*_*_*_*_*"));
    }
    
    @AfterAll
    static void shutdown() throws IOException {
    	if(ctx instanceof Closeable) {
    		((Closeable)ctx).close();
    	}
    }    
}
