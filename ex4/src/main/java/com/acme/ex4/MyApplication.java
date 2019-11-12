package com.acme.ex4;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.dialect.R2dbcDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;

@SpringBootApplication
@EnableR2dbcRepositories
public class MyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
		
	}

	
	@Bean(name="databaseClient") 
	@ConditionalOnProperty(name= {"spring.r2dbc.host", "spring.r2dbc.database", "spring.r2dbc.username", "spring.r2dbc.password"})
	public DatabaseClient databaseClient(Environment env) {
		
		PostgresqlConnectionConfiguration conf = PostgresqlConnectionConfiguration.builder()
				.host(env.getProperty("spring.r2dbc.host"))
				.database(env.getProperty("spring.r2dbc.database"))
				.username(env.getProperty("spring.r2dbc.username"))
				.password(env.getProperty("spring.r2dbc.password"))
				.build();
		
		
		PostgresqlConnectionFactory connFactory = new PostgresqlConnectionFactory(conf);
		DatabaseClient dbClient = DatabaseClient.create(connFactory);
		
		return dbClient;
	}
	
	@Bean(name = "reactiveDataAccessStrategy")
	@ConditionalOnProperty(name= {"spring.r2dbc.dialect"})
	public ReactiveDataAccessStrategy accessStrategy(@Value("${spring.r2dbc.dialect}") Class<? extends R2dbcDialect> dialectClass) throws Exception {
		R2dbcDialect dialect = dialectClass.getConstructor().newInstance();
		return new DefaultReactiveDataAccessStrategy(dialect);
	}

}
