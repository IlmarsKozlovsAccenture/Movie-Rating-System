package com.example.movierating.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingRequestDTOTest {

    @Test
    void createRatingRequestDTO_defaultConstructor() {
        RatingRequestDTO dto = new RatingRequestDTO();
        assertNull(dto.getMovieId());
    }

    @Test
    void createRatingRequestDTO_withConstructor() {
        RatingRequestDTO dto = new RatingRequestDTO(1L, 5, "Excellent");
        assertEquals(1L, dto.getMovieId());
        assertEquals(5, dto.getScore());
        assertEquals("Excellent", dto.getComment());
    }

    @Test
    void ratingRequestDTOSettersAndGetters() {
        RatingRequestDTO dto = new RatingRequestDTO();
        dto.setMovieId(2L);
        dto.setScore(3);
        dto.setComment("Average");

        assertEquals(2L, dto.getMovieId());
        assertEquals(3, dto.getScore());
        assertEquals("Average", dto.getComment());
    }

    @Test
    void ratingRequestDTOMultipleInstances() {
        RatingRequestDTO dto1 = new RatingRequestDTO(1L, 5, "a");
        RatingRequestDTO dto2 = new RatingRequestDTO(1L, 4, "b");
        RatingRequestDTO dto3 = new RatingRequestDTO(2L, 3, "c");

        assertEquals(5, dto1.getScore());
        assertEquals(4, dto2.getScore());
        assertEquals(3, dto3.getScore());
    }
}
