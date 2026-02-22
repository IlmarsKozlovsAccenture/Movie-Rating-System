package com.example.movierating.controller;

import com.example.movierating.dto.MovieDTO;
import com.example.movierating.dto.RatingDTO;
import com.example.movierating.service.MovieService;
import com.example.movierating.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {
    private final MovieService movieService;
    private final RatingService ratingService;

    public MovieController(MovieService movieService, RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @PostMapping("/movies")
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO dto) {
        return ResponseEntity.ok(movieService.createMovie(dto));
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTO>> listMovies() {
        return ResponseEntity.ok(movieService.listMovies());
    }

    @PostMapping("/ratings")
    public ResponseEntity<RatingDTO> addRating(@RequestBody RatingDTO dto) {
        return ResponseEntity.ok(ratingService.addRating(dto));
    }

    @GetMapping("/movies/{id}/average")
    public ResponseEntity<Double> average(@PathVariable Long id) {
        return ResponseEntity.ok(ratingService.averageForMovie(id));
    }
}
