package com.ebook.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Book {
    @JsonProperty("id")
    private int id;
    @JsonProperty("author")
    private String author;
    @JsonProperty("title")
    private String title;
    @JsonProperty("yearOfPublication")
    private Integer yearOfPublication;

    public Book(int id, String author, String title, Integer yearOfPublication) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.yearOfPublication = yearOfPublication;
    }

    public int getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return yearOfPublication == book.yearOfPublication && Objects.equals(author, book.author) && Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, yearOfPublication);
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                '}';
    }
}
