package com.example.goodreads.service;

import com.example.goodreads.model.Book;
import com.example.goodreads.model.Publisher;
import com.example.goodreads.repository.BookJpaRepository;
import com.example.goodreads.repository.PublisherJpaRepository;
import com.example.goodreads.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import com.example.goodreads.model.Author;

@Service
public class BookJpaService implements BookRepository {

    @Autowired
    private BookJpaRepository bookJpaRepository;

    @Autowired
    private PublisherJpaRepository publisherJpaRepository;

    @Override
    @Cacheable(value = "books")
    public ArrayList<Book> getBooks() {
        return new ArrayList<>(bookJpaRepository.findAll());
    }

    @Override
    @Cacheable(value = "book", key = "#bookId")
    public Book getBookById(int bookId) {
        try {
            return bookJpaRepository.findById(bookId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @CacheEvict(value = {"books", "book"}, allEntries = true)
    public Book addBook(Book book) {
        Publisher publisher = book.getPublisher();
        int publisherId = publisher.getPublisherId();
        try {
            publisher = publisherJpaRepository.findById(publisherId).get();
            book.setPublisher(publisher);
            bookJpaRepository.save(book);
            return book;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong publisherId");
        }
    }

    @Override
    @CacheEvict(value = {"books", "book"}, allEntries = true)
    public Book updateBook(int bookId, Book book) {
        try {
            Book newBook = bookJpaRepository.findById(bookId).get();
            if (book.getName() != null) newBook.setName(book.getName());
            if (book.getImageUrl() != null) newBook.setImageUrl(book.getImageUrl());
            if (book.getPublisher() != null) {
                Publisher publisher = book.getPublisher();
                int publisherId = publisher.getPublisherId();
                Publisher newPublisher = publisherJpaRepository.findById(publisherId).get();
                newBook.setPublisher(newPublisher);
            }
            bookJpaRepository.save(newBook);
            return newBook;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @CacheEvict(value = {"books", "book"}, allEntries = true)
    public void deleteBook(int bookId) {
        try {
            bookJpaRepository.deleteById(bookId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    @Cacheable(value = "bookPublisher", key = "#bookId")
    public Publisher getBookPublisher(int bookId) {
        try {
            Book book = bookJpaRepository.findById(bookId).get();
            return book.getPublisher();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Cacheable(value = "bookAuthors", key = "#bookId")
    public List<Author> getBookAuthors(int bookId) {
        try {
            Book book = bookJpaRepository.findById(bookId).get();
            return book.getAuthors();
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
