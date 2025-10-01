package com.stella.assignment2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tags")
public class Tag {

    @EmbeddedId
    private TagId id;

    @Column(name = "tag", nullable = false)
    private String tag;

    @Column(name = "timestamp")
    private Integer timestamp;

    @ManyToOne
    @JoinColumn(name = "movieId", insertable = false, updatable = false)
    private Movie movie;

    public Tag() {}

    public Tag(Integer userId, Integer movieId, String tag, Integer timestamp) {
        this.id = new TagId(userId, movieId);
        this.tag = tag;
        this.timestamp = timestamp;
    }

    public TagId getId() {
        return id;
    }

    public void setId(TagId id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
