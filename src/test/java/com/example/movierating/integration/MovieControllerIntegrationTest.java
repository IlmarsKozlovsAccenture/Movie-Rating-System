package com.example.movierating.integration;

import com.example.movierating.dto.MovieDTO;
import com.example.movierating.dto.RatingDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

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
}
