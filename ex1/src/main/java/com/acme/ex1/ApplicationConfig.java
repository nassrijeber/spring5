package com.acme.ex1;

import com.acme.ex1.dao.impl.FoxMovieDaoImpl;
import com.acme.ex1.dao.impl.WarnerMovieDaoImpl;
import com.acme.ex1.service.MovieService;
import com.acme.ex1.service.impl.MovieServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class ApplicationConfig {
    @Bean
    MovieService service1(FoxMovieDaoImpl dao){
        return new MovieServiceImpl(dao);
    }

    @Bean
    MovieService service2(WarnerMovieDaoImpl dao){
        return new MovieServiceImpl(dao);
    }
}
