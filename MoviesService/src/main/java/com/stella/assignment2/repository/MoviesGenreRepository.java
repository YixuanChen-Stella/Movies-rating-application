package com.stella.assignment2.repository;

import com.stella.assignment2.entity.MoviesGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoviesGenreRepository extends JpaRepository<MoviesGenre, Integer> {
    List<MoviesGenre> findByMovieId(int movieId);
}
