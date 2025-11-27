package com.example.goodreads.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "author")
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authorid")
    private int authorId;

    @Column(name = "authorname")
    private String authorName;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("authors")
    private List<Book> books = new ArrayList<>();

    public Author() {}

    public Author(int authorId, String authorName) {
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public int getAuthorId() {
        return authorId;
    }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public List<Book> getBooks() {
        return books;
    }
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
