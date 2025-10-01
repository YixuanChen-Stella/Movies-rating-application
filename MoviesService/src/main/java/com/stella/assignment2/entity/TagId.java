package com.stella.assignment2.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TagId implements Serializable {

    private Integer userId;
    private Integer movieId;

    public TagId() {}

    public TagId(Integer userId, Integer movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagId tagId = (TagId) o;
        return Objects.equals(userId, tagId.userId) && Objects.equals(movieId, tagId.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, movieId);
    }
}
