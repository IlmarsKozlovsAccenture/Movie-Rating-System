package com.example.movierating.controller;

import com.example.movierating.dto.MovieDTO;
import com.example.movierating.dto.RatingDTO;
import com.example.movierating.service.MovieService;
import com.example.movierating.service.RatingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private RatingService ratingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createMovie_shouldReturnCreatedMovie() throws Exception {
        MovieDTO requestDto = new MovieDTO(null, "Inception", 2010);
        MovieDTO responseDto = new MovieDTO(1L, "Inception", 2010);
        
        when(movieService.createMovie(any(MovieDTO.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Inception"))
                .andExpect(jsonPath("$.year").value(2010));
    }

    @Test
    void listMovies_shouldReturnAllMovies() throws Exception {
        MovieDTO movie1 = new MovieDTO(1L, "Movie1", 2020);
        MovieDTO movie2 = new MovieDTO(2L, "Movie2", 2021);
        List<MovieDTO> movies = Arrays.asList(movie1, movie2);

        when(movieService.listMovies()).thenReturn(movies);

        mockMvc.perform(get("/api/movies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void addRating_shouldReturnAddedRating() throws Exception {
        RatingDTO requestDto = new RatingDTO(null, 1L, 5, "Excellent");
        RatingDTO responseDto = new RatingDTO(10L, 1L, 5, "Excellent");

        when(ratingService.addRating(any(RatingDTO.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/ratings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.movieId").value(1L))
                .andExpect(jsonPath("$.score").value(5));
    }

    @Test
    void getMovieAverage_shouldReturnAverageRating() throws Exception {
        when(ratingService.averageForMovie(1L)).thenReturn(4.5);

        mockMvc.perform(get("/api/movies/1/average")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("4.5"));
    }

    @Test
    void getMovieAverage_noRatings() throws Exception {
        when(ratingService.averageForMovie(99L)).thenReturn(0.0);

        mockMvc.perform(get("/api/movies/99/average"))
                .andExpect(status().isOk())
                .andExpect(content().string("0.0"));
    }
}
