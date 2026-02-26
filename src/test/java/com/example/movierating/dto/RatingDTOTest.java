package com.example.movierating.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingDTOTest {

    @Test
    void createRatingResponseDTO_defaultConstructor() {
        RatingResponseDTO dto = new RatingResponseDTO();
        assertNull(dto.getId());
        assertNull(dto.getMovieId());
    }

    @Test
    void createRatingResponseDTO_withConstructor() {
        RatingResponseDTO dto = new RatingResponseDTO(10L, 1L, 5, "Excellent");
        assertEquals(10L, dto.getId());
        assertEquals(1L, dto.getMovieId());
        assertEquals(5, dto.getScore());
        assertEquals("Excellent", dto.getComment());
    }

    @Test
    void ratingResponseDTOSettersAndGetters() {
        RatingResponseDTO dto = new RatingResponseDTO();
        dto.setId(20L);
        dto.setMovieId(2L);
        dto.setScore(3);
        dto.setComment("Average");

        assertEquals(20L, dto.getId());
        assertEquals(2L, dto.getMovieId());
        assertEquals(3, dto.getScore());
        assertEquals("Average", dto.getComment());
    }

    @Test
    void ratingResponseDTOMultipleInstances() {
        RatingResponseDTO dto1 = new RatingResponseDTO(1L, 1L, 5, "a");
        RatingResponseDTO dto2 = new RatingResponseDTO(2L, 1L, 4, "b");
        RatingResponseDTO dto3 = new RatingResponseDTO(3L, 2L, 3, "c");

        assertEquals(5, dto1.getScore());
        assertEquals(4, dto2.getScore());
        assertEquals(3, dto3.getScore());
    }
}
