package com.example.movierating.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingDTOTest {

    @Test
    void createRatingDTO_defaultConstructor() {
        RatingDTO dto = new RatingDTO();
        assertNull(dto.getId());
        assertNull(dto.getMovieId());
    }

    @Test
    void createRatingDTO_withConstructor() {
        RatingDTO dto = new RatingDTO(10L, 1L, 5, "Excellent");
        assertEquals(10L, dto.getId());
        assertEquals(1L, dto.getMovieId());
        assertEquals(5, dto.getScore());
        assertEquals("Excellent", dto.getComment());
    }

    @Test
    void ratingDTOSettersAndGetters() {
        RatingDTO dto = new RatingDTO();
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
    void ratingDTOMultipleInstances() {
        RatingDTO dto1 = new RatingDTO(1L, 1L, 5, "a");
        RatingDTO dto2 = new RatingDTO(2L, 1L, 4, "b");
        RatingDTO dto3 = new RatingDTO(3L, 2L, 3, "c");

        assertEquals(5, dto1.getScore());
        assertEquals(4, dto2.getScore());
        assertEquals(3, dto3.getScore());
    }
}
