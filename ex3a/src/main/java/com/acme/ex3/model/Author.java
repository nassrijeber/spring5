package com.acme.ex3.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.acme.common.model.AbstractPersistentEntity;


@Entity @Cacheable
public class Author extends AbstractPersistentEntity<Integer> {

	
    private String firstname;

    private String lastname;

    @OneToMany(mappedBy = "author")
    private List<Book> publications;


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Book> getPublications() {
        return publications;
    }

    public void setPublications(List<Book> publications) {
        this.publications = publications;
    }

    public String getFullname(){
        return this.firstname+" "+this.lastname;
    }
}
