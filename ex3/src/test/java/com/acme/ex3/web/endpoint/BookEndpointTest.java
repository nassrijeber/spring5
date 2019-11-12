package com.acme.ex3.web.endpoint;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.acme.ex3.ApplicationConfig;


//@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={ApplicationConfig.class})
@WebAppConfiguration
@SpringBootTest
class BookEndpointTest {


	private final MockMvc mockMvc;
	
	BookEndpointTest(WebApplicationContext ctx) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
		
	@Test 
	void getBooks200() throws Exception {
		this.mockMvc.perform(
				get("/books")
				.param("title", "ar")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void getSearch200() throws Exception {
		String json = mapper.writeValueAsString(Map.of("title", "ar"));
		this.mockMvc.perform(
				post("/searches")
				.param("domain", "books")
				.content(json)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	@Test 
	void getBooks400() throws Exception {
		this.mockMvc.perform(
				get("/books")
				.param("title", "")
				.accept(MediaType.APPLICATION_JSON)				
				)
		.andExpect(status().isBadRequest())
		.andDo(print());
	}
	
	@Test 
	void getSearch400() throws Exception {
		String json = mapper.writeValueAsString(Map.of("title", ""));
		this.mockMvc.perform(
				post("/searches")
				.param("domain", "books")
				.content(json)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isBadRequest())
		.andDo(print());
	}
	
	@Test
	void getBook200() throws Exception {
		this.mockMvc.perform(
				get("/books/1")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	@Test
	void getBook404() throws Exception {
		this.mockMvc.perform(
				get("/books/128")
				.accept(MediaType.APPLICATION_JSON)
				)
		.andExpect(status().isNotFound())
		.andDo(print());
	}
}