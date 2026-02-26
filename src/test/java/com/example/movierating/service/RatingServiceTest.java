package com.example.movierating.service;

import com.example.movierating.dto.RatingRequestDTO;
import com.example.movierating.dto.RatingResponseDTO;
import com.example.movierating.entity.Rating;
import com.example.movierating.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

        RatingRequestDTO dto = new RatingRequestDTO(1L, 4, "Good");
        RatingResponseDTO saved = service.addRating(dto);

        assertNotNull(saved.getId());
        assertEquals(4, saved.getScore());
        assertEquals(1L, saved.getMovieId());
        assertEquals("Good", saved.getComment());
    }

    @Test
    void addRating_throwsExceptionOnDataIntegrityViolation() {
        when(repo.save(any(Rating.class)))
                .thenThrow(new DataIntegrityViolationException("unique constraint violation"));

        RatingRequestDTO dto = new RatingRequestDTO(1L, 4, "Good");
        assertThrows(RatingAlreadyExistsException.class, () -> service.addRating(dto));
    }

    @Test
    void averageForMovie_returnsAverage() {
        Rating r1 = new Rating(1L, 4, "a");
        Rating r2 = new Rating(1L, 2, "b");
        when(repo.findByMovieId(1L)).thenReturn(List.of(r1, r2));

        double avg = service.averageForMovie(1L);
        assertEquals(3.0, avg);
    }

    @Test
    void averageForMovie_singleRating() {
        Rating r = new Rating(1L, 5, "Excellent");
        when(repo.findByMovieId(1L)).thenReturn(List.of(r));

        double avg = service.averageForMovie(1L);
        assertEquals(5.0, avg);
    }

    @Test
    void averageForMovie_emptyRatings() {
        when(repo.findByMovieId(anyLong())).thenReturn(Collections.emptyList());

        double avg = service.averageForMovie(99L);
        assertEquals(0.0, avg);
    }

    @Test
    void averageForMovie_multipleRatings() {
        Rating r1 = new Rating(2L, 5, "a");
        Rating r2 = new Rating(2L, 3, "b");
        Rating r3 = new Rating(2L, 4, "c");
        when(repo.findByMovieId(2L)).thenReturn(List.of(r1, r2, r3));

        double avg = service.averageForMovie(2L);
        assertEquals(4.0, avg);
    }

    @Test
    void ratingsForMovie_returnsRatings() {
        Rating r1 = new Rating(1L, 5, "Great");
        r1.setId(10L);
        Rating r2 = new Rating(1L, 4, "Good");
        r2.setId(11L);
        when(repo.findByMovieId(1L)).thenReturn(List.of(r1, r2));

        List<RatingResponseDTO> ratings = service.ratingsForMovie(1L);
        assertEquals(2, ratings.size());
        assertEquals(10L, ratings.get(0).getId());
        assertEquals(5, ratings.get(0).getScore());
    }

    @Test
    void ratingsForMovie_emptyList() {
        when(repo.findByMovieId(anyLong())).thenReturn(Collections.emptyList());

        List<RatingResponseDTO> ratings = service.ratingsForMovie(99L);
        assertEquals(0, ratings.size());
        assertTrue(ratings.isEmpty());
    }
}
