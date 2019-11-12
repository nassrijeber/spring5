package com.acme.ex4.model;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Book")
public class Book extends AbstractPersistentEntity<Integer> {

	@Column("title")
    private String title;
    
	public Book() {
		super();
	}
	
    public Book(String title) {
		this();
		this.title = title;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	@Override
	public String toString() {
		return "Book [title=" + title + ", id=" + getId() + "]";
	}    
    
    
}
