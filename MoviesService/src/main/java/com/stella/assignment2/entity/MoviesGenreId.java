package com.stella.assignment2.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MoviesGenreId implements Serializable {

    private Integer movieId;
    private Integer genreId;

    public MoviesGenreId() {}

    public MoviesGenreId(Integer movieId, Integer genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }

    // Getters and Setters
    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    // Equals and hashCode methods for correct comparison in collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoviesGenreId that = (MoviesGenreId) o;
        return movieId.equals(that.movieId) && genreId.equals(that.genreId);
    }

    @Override
    public int hashCode() {
        return 31 * movieId.hashCode() + genreId.hashCode();
    }
}
