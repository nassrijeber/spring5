package com.acme.ex3.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest
@WebAppConfiguration
class BookControllerTest {

	private MockMvc mockMvc;

	BookControllerTest(WebApplicationContext ctx) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();	
	}
	
	@Test
	void getBooks() throws Exception {
		this.mockMvc.perform(
				get("/books")
				)
//		.andExpect(model().attributeExists("example"))
		.andExpect(model().attributeExists("book"))
		.andExpect(model().attributeDoesNotExist("results"))
		.andExpect(view().name("books/list"));
	}
	
	@Test
	void getBooksWithResults() throws Exception {
		this.mockMvc.perform(
				get("/books")
				.param("title", "ar")
				)
		.andExpect(model().attributeExists("example", "results"))
		.andExpect(view().name("/books/list"));
	}
	
	@Test
	void getBooksInvalidInput() throws Exception {
		this.mockMvc.perform(
				get("/books")
				.param("title", "")
				)
		.andExpect(model().attributeExists("example"))
		.andExpect(model().attributeHasFieldErrors("example"))
		.andExpect(model().attributeDoesNotExist("results"))
		.andExpect(view().name("/books/list"));
	}
	
	@Test
	void getBook() throws Exception {
		this.mockMvc.perform(
				get("/books/1")
				)
		.andExpect(model().attributeExists("entity", "reservationCommand"))
		.andExpect(view().name("/books/detail"));
	}
	
	@Test
	void getBookNotFound() throws Exception {
		this.mockMvc.perform(
				get("/books/128")
				)
		.andExpect(model().attributeDoesNotExist("entity"))
		.andExpect(view().name("/books/detail"));
	}
}
