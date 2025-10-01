package com.stella.assignment2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {

    @EmbeddedId
    private RatingId id;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "timestamp")
    private Integer timestamp;

    @ManyToOne
    @JoinColumn(name = "movieId", referencedColumnName = "movieId", insertable = false, updatable = false)
    private Movie movie;

    public Rating() {}

    public Rating(Integer userId, Integer movieId, Double rating, Integer timestamp) {
        this.id = new RatingId(userId, movieId);
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public RatingId getId() {
        return id;
    }

    public void setId(RatingId id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
