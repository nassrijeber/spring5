package com.acme.ex1.model;

public class Movie {
    private final String title;
    private final int year;

    public Movie(String title, int year) {
        super();
        this.title = title;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }
}
