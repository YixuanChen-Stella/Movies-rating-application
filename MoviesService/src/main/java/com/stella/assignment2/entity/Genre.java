package com.stella.assignment2.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer genreId;

    private String genre;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MoviesGenre> moviesGenres = new HashSet<>();

    public Genre() {}

    public Genre(String genre) {
        this.genre = genre;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Set<MoviesGenre> getMoviesGenres() {
        return moviesGenres;
    }

    public void setMoviesGenres(Set<MoviesGenre> moviesGenres) {
        this.moviesGenres = moviesGenres;
    }
}
