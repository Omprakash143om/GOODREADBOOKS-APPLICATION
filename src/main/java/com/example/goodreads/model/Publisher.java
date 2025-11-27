package com.example.goodreads.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "publisher")
public class Publisher implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisherid")
    private int publisherId;

    @Column(name = "publishername")
    private String publisherName;

    public Publisher() {}
    public Publisher(int publisherId, String publisherName) {
        this.publisherId = publisherId;
        this.publisherName = publisherName;
    }

    public int getPublisherId() { return publisherId; }
    public void setPublisherId(int publisherId) { this.publisherId = publisherId; }
    public String getPublisherName() { return publisherName; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }
}
