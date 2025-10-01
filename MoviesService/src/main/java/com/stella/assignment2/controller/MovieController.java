package com.stella.assignment2.controller;

import com.stella.assignment2.entity.Link;
import com.stella.assignment2.entity.Movie;
import com.stella.assignment2.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class MovieController {

    @Autowired
    private MovieService movieService;
    @GetMapping("/movie/{id}")
    public ResponseEntity<Object> getMovie(@PathVariable int id) {
        return getMovieEntity(id);
    }

    @GetMapping("/movie")
    public ResponseEntity<Object> getMovieById(@RequestParam("id") int movieId) {
        return getMovieEntity(movieId);
    }

    private ResponseEntity<Object> getMovieEntity(int movieId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache");

        MovieResponse movieDetails = movieService.getMovieWithGenres(movieId);
        if (movieDetails != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("movieId", movieDetails.getMovieId());
            map.put("title", movieDetails.getTitle());
            map.put("genres", movieDetails.getGenres());
            return new ResponseEntity<>(map, headers, HttpStatus.OK);
        } else {
            Map<String,String> map = new HashMap<>();
            map.put("error", "Movie not found");
            return new ResponseEntity<>(map, headers, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/rating/{id}")
    public ResponseEntity<Object> getAverageRatings(@PathVariable int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache");

        try {
            Double averageRating = movieService.getAverageRating(id);
            if (averageRating != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("movieId", id);
                response.put("average_rating", averageRating);
                return new ResponseEntity<>(response, headers, HttpStatus.OK);
            } else {
                Map<String,String> map = new HashMap<>();
                map.put("error", "No ratings found for this movie");
                return new ResponseEntity<>(map, headers, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            headers.add("Error-Message", e.getMessage() + e.getLocalizedMessage());
            return new ResponseEntity<>(new ErrorResponse("Service Unavailable"), headers, HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/link/{id}")
    public ResponseEntity<Object> getLinkOfMovie(@PathVariable int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache");

        Link link = movieService.getLinkOfMovie(id);

        if (link != null) {
            Map<String, Object> response = Map.of(
                    "movieId", link.getMovieId(),
                    "imdbId", link.getImdbId().trim(),
                    "tmdbId", link.getTmdbId().trim()
            );
            return new ResponseEntity<>(response, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/movies")
    public ResponseEntity<Object> getMovies() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache");

        List<Movie> movies = movieService.getAllMovies();
        return new ResponseEntity<>(movies, headers, HttpStatus.OK);
    }


    // MovieResponse to handle movie details with genres
    public static class MovieResponse {
        private int movieId;
        private String title;
        private List<String> genres;

        public MovieResponse(int movieId, String title, List<String> genres) {
            this.movieId = movieId;
            this.title = title;
            this.genres = genres;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }
    }
}
