package com.example.movierating.integration;

import com.example.movierating.dto.MovieRequestDTO;
import com.example.movierating.dto.MovieResponseDTO;
import com.example.movierating.dto.RatingRequestDTO;
import com.example.movierating.dto.RatingResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieControllerIntegrationTest {
    @Autowired
    private TestRestTemplate rest;

    @Test
    void createAndRateMovie_flow() {
        MovieRequestDTO movie = new MovieRequestDTO("Integration", 2021);
        ResponseEntity<MovieResponseDTO> r1 = rest.postForEntity("/api/movies", movie, MovieResponseDTO.class);
        assertEquals(200, r1.getStatusCodeValue());
        Long id = r1.getBody().getId();

        RatingRequestDTO rating = new RatingRequestDTO(id, 5, "Nice");
        ResponseEntity<RatingResponseDTO> r2 = rest.postForEntity("/api/ratings", rating, RatingResponseDTO.class);
        assertEquals(200, r2.getStatusCodeValue());

        ResponseEntity<Double> avg = rest.getForEntity("/api/movies/" + id + "/average", Double.class);
        assertEquals(200, avg.getStatusCodeValue());
        assertTrue(avg.getBody() > 0);
    }

    @Test
    void createMultipleMovies_listAll() {
        MovieRequestDTO movie1 = new MovieRequestDTO("Inception", 2010);
        MovieRequestDTO movie2 = new MovieRequestDTO("Matrix", 1999);

        ResponseEntity<MovieResponseDTO> r1 = rest.postForEntity("/api/movies", movie1, MovieResponseDTO.class);
        ResponseEntity<MovieResponseDTO> r2 = rest.postForEntity("/api/movies", movie2, MovieResponseDTO.class);

        assertEquals(200, r1.getStatusCodeValue());
        assertEquals(200, r2.getStatusCodeValue());

        ResponseEntity<MovieResponseDTO[]> list = rest.getForEntity("/api/movies", MovieResponseDTO[].class);
        assertEquals(200, list.getStatusCodeValue());
        assertTrue(list.getBody().length >= 2);
    }

    @Test
    void multipleRatings_averageCalculation() {
        MovieRequestDTO movie = new MovieRequestDTO("Test Movie", 2020);
        ResponseEntity<MovieResponseDTO> movieResponse = rest.postForEntity("/api/movies", movie, MovieResponseDTO.class);
        Long movieId = movieResponse.getBody().getId();

        // Add multiple ratings
        for (int i = 1; i <= 5; i++) {
            RatingRequestDTO rating = new RatingRequestDTO(movieId, i, "Rating " + i);
            ResponseEntity<RatingResponseDTO> ratingResponse = rest.postForEntity("/api/ratings", rating, RatingResponseDTO.class);
            assertEquals(200, ratingResponse.getStatusCodeValue());
        }

        // Get average (should be 3.0: (1+2+3+4+5)/5 = 15/5 = 3)
        ResponseEntity<Double> avg = rest.getForEntity("/api/movies/" + movieId + "/average", Double.class);
        assertEquals(200, avg.getStatusCodeValue());
        assertEquals(3.0, avg.getBody(), 0.01);
    }

    @Test
    void addRatingToNonExistentMovie() {
        RatingRequestDTO rating = new RatingRequestDTO(99999L, 5, "Test");
        ResponseEntity<RatingResponseDTO> response = rest.postForEntity("/api/ratings", rating, RatingResponseDTO.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void createDuplicateMovie_shouldReturnConflict() {
        MovieRequestDTO movie = new MovieRequestDTO("DuplicateTest", 2022);
        
        // Create first movie
        ResponseEntity<MovieResponseDTO> r1 = rest.postForEntity("/api/movies", movie, MovieResponseDTO.class);
        assertEquals(200, r1.getStatusCodeValue());

        // Try to create same movie again
        ResponseEntity<?> r2 = rest.postForEntity("/api/movies", movie, Object.class);
        assertEquals(409, r2.getStatusCodeValue());
    }
}
