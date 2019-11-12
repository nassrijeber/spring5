package com.acme.ex1.service.impl;

import com.acme.ex1.service.MovieService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

import java.io.Closeable;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MovieServiceImplTest {

	private static ApplicationContext ctx;

	private static MovieService service;
    
    @BeforeAll
    static void setup() {
        // TODO : valorisation du champ ctx

    	// TODO : valorisation du champ service
    	
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
    	System.out.println(ctx);
        assertNotNull(service.find("_*_*_*_*_*_*"));
    }

    @AfterAll
    static void shutdown() throws IOException {
    	if(ctx instanceof Closeable) {
    		((Closeable)ctx).close();
    	}
    }

}
