package com.acme.ex3.web.dto;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.acme.ex3.model.Book;
import com.acme.ex3.model.Comment;

public class BookDto{

	public static class CommentDto{
		public String author;
		public String text;
		public Instant date;
		public CommentDto(Comment entity) {
			super();	
			this.author=entity.getMember().getAccount().getUsername();
			this.text = entity.getText();
			this.date = entity.getDate();
		}	
	}
	
	
	public Integer id;
	public String title,author,category;
	public List<CommentDto> comments;
	public Map<String, String> links = new HashMap<>();
	
	public BookDto(Book entity, boolean withComments) {
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.author = entity.getAuthor().getFullname();
		this.category = entity.getCategory().getName();
		
		if(withComments) {
			this.comments = entity.getComments().stream().map(CommentDto::new).collect(Collectors.toList());
		}
		this.links.put("author", "/authors/"+entity.getAuthor().getId());
		this.links.put("comments", "/book/"+entity.getId()+"/comments");
		this.links.put("self", "/book/"+entity.getId());
	}
}