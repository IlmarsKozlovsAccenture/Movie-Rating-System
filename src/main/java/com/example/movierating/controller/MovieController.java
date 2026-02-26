package com.example.movierating.controller;

import com.example.movierating.dto.MovieRequestDTO;
import com.example.movierating.dto.MovieResponseDTO;
import com.example.movierating.dto.RatingRequestDTO;
import com.example.movierating.dto.RatingResponseDTO;
import com.example.movierating.service.MovieAlreadyExistsException;
import com.example.movierating.service.MovieService;
import com.example.movierating.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> createMovie(@RequestBody MovieRequestDTO dto) {
        try {
            MovieResponseDTO result = movieService.createMovie(dto);
            return ResponseEntity.ok(result);
        } catch (MovieAlreadyExistsException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieResponseDTO>> listMovies() {
        return ResponseEntity.ok(movieService.listMovies());
    }

    @PostMapping("/ratings")
    public ResponseEntity<RatingResponseDTO> addRating(@RequestBody RatingRequestDTO dto) {
        return ResponseEntity.ok(ratingService.addRating(dto));
    }

    @GetMapping("/movies/{id}/average")
    public ResponseEntity<Double> average(@PathVariable Long id) {
        return ResponseEntity.ok(ratingService.averageForMovie(id));
    }
}
