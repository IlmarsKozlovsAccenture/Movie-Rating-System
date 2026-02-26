package com.example.movierating.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieDTOTest {

    @Test
    void createMovieResponseDTO_defaultConstructor() {
        MovieResponseDTO dto = new MovieResponseDTO();
        assertNull(dto.getId());
        assertNull(dto.getTitle());
        assertNull(dto.getYear());
    }

    @Test
    void createMovieResponseDTO_withConstructor() {
        MovieResponseDTO dto = new MovieResponseDTO(1L, "Inception", 2010);
        assertEquals(1L, dto.getId());
        assertEquals("Inception", dto.getTitle());
        assertEquals(2010, dto.getYear());
    }

    @Test
    void movieResponseDTOSettersAndGetters() {
        MovieResponseDTO dto = new MovieResponseDTO();
        dto.setId(5L);
        dto.setTitle("Matrix");
        dto.setYear(1999);

        assertEquals(5L, dto.getId());
        assertEquals("Matrix", dto.getTitle());
        assertEquals(1999, dto.getYear());
    }

    @Test
    void movieResponseDTOWithNullValues() {
        MovieResponseDTO dto = new MovieResponseDTO(null, null, null);
        assertNull(dto.getId());
        assertNull(dto.getTitle());
        assertNull(dto.getYear());
    }
}
