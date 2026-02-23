package com.example.movierating.integration;

import com.example.movierating.dto.MovieDTO;
import com.example.movierating.dto.RatingDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MovieControllerIntegrationTest {
    @Autowired
    private TestRestTemplate rest;

    @Test
    void createAndRateMovie_flow() {
        MovieDTO movie = new MovieDTO(null, "Integration", 2021);
        ResponseEntity<MovieDTO> r1 = rest.postForEntity("/api/movies", movie, MovieDTO.class);
        assertEquals(200, r1.getStatusCodeValue());
        Long id = r1.getBody().getId();

        RatingDTO rating = new RatingDTO(null, id, 5, "Nice");
        ResponseEntity<RatingDTO> r2 = rest.postForEntity("/api/ratings", rating, RatingDTO.class);
        assertEquals(200, r2.getStatusCodeValue());

        ResponseEntity<Double> avg = rest.getForEntity("/api/movies/" + id + "/average", Double.class);
        assertEquals(200, avg.getStatusCodeValue());
        assertTrue(avg.getBody() > 0);
    }

    @Test
    void createMultipleMovies_listAll() {
        MovieDTO movie1 = new MovieDTO(null, "Inception", 2010);
        MovieDTO movie2 = new MovieDTO(null, "Matrix", 1999);

        ResponseEntity<MovieDTO> r1 = rest.postForEntity("/api/movies", movie1, MovieDTO.class);
        ResponseEntity<MovieDTO> r2 = rest.postForEntity("/api/movies", movie2, MovieDTO.class);

        assertEquals(200, r1.getStatusCodeValue());
        assertEquals(200, r2.getStatusCodeValue());

        ResponseEntity<MovieDTO[]> list = rest.getForEntity("/api/movies", MovieDTO[].class);
        assertEquals(200, list.getStatusCodeValue());
        assertTrue(list.getBody().length >= 2);
    }

    @Test
    void multipleRatings_averageCalculation() {
        MovieDTO movie = new MovieDTO(null, "Test Movie", 2020);
        ResponseEntity<MovieDTO> movieResponse = rest.postForEntity("/api/movies", movie, MovieDTO.class);
        Long movieId = movieResponse.getBody().getId();

        // Add multiple ratings
        for (int i = 1; i <= 5; i++) {
            RatingDTO rating = new RatingDTO(null, movieId, i, "Rating " + i);
            ResponseEntity<RatingDTO> ratingResponse = rest.postForEntity("/api/ratings", rating, RatingDTO.class);
            assertEquals(200, ratingResponse.getStatusCodeValue());
        }

        // Get average (should be 3.0: (1+2+3+4+5)/5 = 15/5 = 3)
        ResponseEntity<Double> avg = rest.getForEntity("/api/movies/" + movieId + "/average", Double.class);
        assertEquals(200, avg.getStatusCodeValue());
        assertEquals(3.0, avg.getBody(), 0.01);
    }

    @Test
    void addRatingToNonExistentMovie() {
        RatingDTO rating = new RatingDTO(null, 99999L, 5, "Test");
        ResponseEntity<RatingDTO> response = rest.postForEntity("/api/ratings", rating, RatingDTO.class);
        assertEquals(200, response.getStatusCodeValue());
    }
}
