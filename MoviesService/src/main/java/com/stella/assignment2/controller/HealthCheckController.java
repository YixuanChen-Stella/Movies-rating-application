package com.stella.assignment2.controller;

import com.stella.assignment2.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1")
public class HealthCheckController {

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/healthcheck")
    public ResponseEntity<String> healthCheck(@RequestParam Map<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache");

        if (!params.isEmpty()) {
            return new ResponseEntity<>("Parameters are not allowed.", headers, HttpStatus.BAD_REQUEST);
        }
        movieService.getMovieById(1);

        return new ResponseEntity<>("OK", headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/healthcheck", method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<String> handleInvalidMethod() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache");
        return new ResponseEntity<>("Bad Request.", headers, HttpStatus.BAD_REQUEST);
    }
}