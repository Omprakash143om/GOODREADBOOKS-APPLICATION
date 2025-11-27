package com.example.goodreads.service;

import com.example.goodreads.model.Author;
import com.example.goodreads.repository.AuthorJpaRepository;
import com.example.goodreads.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorJpaService implements AuthorRepository {

    @Autowired
    private AuthorJpaRepository authorJpaRepository;

    @Override
    @Cacheable(value = "authors")
    public ArrayList<Author> getAuthors() {
        List<Author> authorList = authorJpaRepository.findAll();
        return new ArrayList<>(authorList);
    }

    @Override
    @Cacheable(value = "author", key = "#authorId")
    public Author getAuthorById(int authorId) {
        try {
            return authorJpaRepository.findById(authorId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @CacheEvict(value = {"authors", "author"}, allEntries = true)
    public Author addAuthor(Author author) {
        authorJpaRepository.save(author);
        return author;
    }

    @Override
    @CacheEvict(value = {"authors", "author"}, allEntries = true)
    public Author updateAuthor(int authorId, Author author) {
        try {
            Author new_author = authorJpaRepository.findById(authorId).get();
            if (author.getAuthorName() != null) new_author.setAuthorName(author.getAuthorName());
            authorJpaRepository.save(new_author);
            return new_author;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @CacheEvict(value = {"authors", "author"}, allEntries = true)
    public void deleteAuthor(int authorId) {
        try {
            authorJpaRepository.deleteById(authorId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
}
