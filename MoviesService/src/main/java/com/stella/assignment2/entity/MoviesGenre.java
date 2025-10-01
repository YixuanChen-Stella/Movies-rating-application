package com.stella.assignment2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "movies_genres")
public class MoviesGenre {

    @EmbeddedId
    private MoviesGenreId id;

    @ManyToOne
    @MapsId("movieId")
    @JoinColumn(name = "movieId", referencedColumnName = "movieId", insertable = false, updatable = false)
    private Movie movie;

    @ManyToOne
    @MapsId("genreId")
    @JoinColumn(name = "genreId", referencedColumnName = "genreId", insertable = false, updatable = false)
    private Genre genre;

    @Column(name = "movieId", insertable = false, updatable = false)
    private Integer movieId;

    @Column(name = "genreId", insertable = false, updatable = false)
    private Integer genreId;

    // Getters and Setters
    public MoviesGenreId getId() {
        return id;
    }

    public void setId(MoviesGenreId id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

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
}
