package com.techreturners.recordshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity

public class MusicRecord {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column
    String albumName;

    @Column
    String artist;

    @Column
    Integer releaseYear;

    @Column
    Long stock;

    @Column
    MusicGenre genre;
}
