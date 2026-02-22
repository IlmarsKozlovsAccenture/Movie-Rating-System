package com.example.movierating.service;

import com.example.movierating.dto.RatingDTO;
import com.example.movierating.entity.Rating;
import com.example.movierating.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RatingServiceTest {
    private RatingRepository repo;
    private RatingService service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(RatingRepository.class);
        service = new RatingService(repo);
    }

    @Test
    void addRating_savesAndReturnsDto() {
        Rating r = new Rating(1L, 4, "Good");
        r.setId(10L);
        when(repo.save(any(Rating.class))).thenReturn(r);

        RatingDTO dto = new RatingDTO(null, 1L, 4, "Good");
        RatingDTO saved = service.addRating(dto);

        assertNotNull(saved.getId());
        assertEquals(4, saved.getScore());
    }

    @Test
    void averageForMovie_returnsAverage() {
        Rating r1 = new Rating(1L, 4, "a");
        Rating r2 = new Rating(1L, 2, "b");
        when(repo.findByMovieId(1L)).thenReturn(List.of(r1, r2));

        double avg = service.averageForMovie(1L);
        assertEquals(3.0, avg);
    }
}
