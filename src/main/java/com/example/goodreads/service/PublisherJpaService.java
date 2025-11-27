package com.example.goodreads.service;

import com.example.goodreads.model.Publisher;
import com.example.goodreads.repository.PublisherJpaRepository;
import com.example.goodreads.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublisherJpaService implements PublisherRepository {

    @Autowired
    private PublisherJpaRepository publisherJpaRepository;

    @Override
    @Cacheable(value = "publishers")
    public ArrayList<Publisher> getPublishers() {
        return new ArrayList<>(publisherJpaRepository.findAll());
    }

    @Override
    @Cacheable(value = "publisher", key = "#publisherId")
    public Publisher getPublisherById(int publisherId) {
        try {
            return publisherJpaRepository.findById(publisherId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @CacheEvict(value = {"publishers", "publisher"}, allEntries = true)
    public Publisher addPublisher(Publisher publisher) {
        publisherJpaRepository.save(publisher);
        return publisher;
    }

    @Override
    @CacheEvict(value = {"publishers", "publisher"}, allEntries = true)
    public Publisher updatePublisher(int publisherId, Publisher publisher) {
        try {
            Publisher new_publisher = publisherJpaRepository.findById(publisherId).get();
            if (publisher.getPublisherName() != null) new_publisher.setPublisherName(publisher.getPublisherName());
            publisherJpaRepository.save(new_publisher);
            return new_publisher;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @CacheEvict(value = {"publishers", "publisher"}, allEntries = true)
    public void deletePublisher(int publisherId) {
        try {
            publisherJpaRepository.deleteById(publisherId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
}
