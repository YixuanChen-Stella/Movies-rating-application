package com.stella.assignment2.service;

import com.stella.assignment2.controller.MovieController;
import com.stella.assignment2.entity.Link;
import com.stella.assignment2.entity.Movie;
import com.stella.assignment2.entity.MoviesGenre;
import com.stella.assignment2.entity.Rating;
import com.stella.assignment2.repository.MovieRepository;
import com.stella.assignment2.repository.MoviesGenreRepository;
import com.stella.assignment2.repository.RatingsRepository;
import com.stella.assignment2.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MoviesGenreRepository moviesGenreRepository;

    @Autowired
    private RatingsRepository ratingRepository;

    @Autowired
    private LinkRepository linkRepository;

    public Link getLinkOfMovie(int movieId) {
        return linkRepository.findByMovieId(movieId);
    }

    public Optional<Movie> getMovieById(int movieId) {
        try {
            return movieRepository.findById(movieId);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Movie> getAllMovies() {
        try {
            return movieRepository.findAll();
        } catch (DataAccessException e) {
            return List.of();
        }
    }

    public MovieController.MovieResponse getMovieWithGenres(int movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            String title = movie.get().getTitle();
            int movieIdFromMovie = movie.get().getMovieId();
            List<MoviesGenre> moviesGenres = moviesGenreRepository.findByMovieId(movieId);
            List<String> genres = moviesGenres.stream()
                    .map(mg -> mg.getGenre().getGenre())
                    .collect(Collectors.toList());

            return new MovieController.MovieResponse(movieIdFromMovie, title, genres);
        }
        return null;
    }

    public Double getAverageRating(int movieId) {
        List<Rating> ratings = ratingRepository.findById_MovieId(movieId);
        if (ratings != null && !ratings.isEmpty()) {
            return ratings.stream()
                    .mapToDouble(rating -> rating.getRating() != null ? rating.getRating() : 0.0)
                    .average()
                    .orElse(0.0);
        }
        return null;
    }
}
