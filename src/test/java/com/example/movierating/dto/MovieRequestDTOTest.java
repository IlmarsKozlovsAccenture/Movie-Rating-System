package com.example.movierating.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieRequestDTOTest {

    @Test
    void createMovieRequestDTO_defaultConstructor() {
        MovieRequestDTO dto = new MovieRequestDTO();
        assertNull(dto.getTitle());
        assertNull(dto.getYear());
    }

    @Test
    void createMovieRequestDTO_withConstructor() {
        MovieRequestDTO dto = new MovieRequestDTO("Inception", 2010);
        assertEquals("Inception", dto.getTitle());
        assertEquals(2010, dto.getYear());
    }

    @Test
    void movieRequestDTOSettersAndGetters() {
        MovieRequestDTO dto = new MovieRequestDTO();
        dto.setTitle("Matrix");
        dto.setYear(1999);

        assertEquals("Matrix", dto.getTitle());
        assertEquals(1999, dto.getYear());
    }

    @Test
    void movieRequestDTOWithNullValues() {
        MovieRequestDTO dto = new MovieRequestDTO(null, null);
        assertNull(dto.getTitle());
        assertNull(dto.getYear());
    }
}
