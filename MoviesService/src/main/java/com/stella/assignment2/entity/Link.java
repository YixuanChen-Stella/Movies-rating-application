package com.stella.assignment2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "links")
public class Link {

    @Id
    @Column(name = "movieId")
    private Integer movieId;

    @Column(name = "imdbId")
    private String imdbId;

    @Column(name = "tmdbId")
    private String tmdbId;

    public Link() {}

    public Link(Integer movieId, String imdbId, String tmdbId) {
        this.movieId = movieId;
        this.imdbId = imdbId;
        this.tmdbId = tmdbId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(String tmdbId) {
        this.tmdbId = tmdbId;
    }
}
