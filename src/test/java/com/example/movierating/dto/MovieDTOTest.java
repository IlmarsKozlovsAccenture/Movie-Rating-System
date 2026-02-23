package com.example.movierating.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieDTOTest {

    @Test
    void createMovieDTO_defaultConstructor() {
        MovieDTO dto = new MovieDTO();
        assertNull(dto.getId());
        assertNull(dto.getTitle());
        assertNull(dto.getYear());
    }

    @Test
    void createMovieDTO_withConstructor() {
        MovieDTO dto = new MovieDTO(1L, "Inception", 2010);
        assertEquals(1L, dto.getId());
        assertEquals("Inception", dto.getTitle());
        assertEquals(2010, dto.getYear());
    }

    @Test
    void movieDTOSettersAndGetters() {
        MovieDTO dto = new MovieDTO();
        dto.setId(5L);
        dto.setTitle("Matrix");
        dto.setYear(1999);

        assertEquals(5L, dto.getId());
        assertEquals("Matrix", dto.getTitle());
        assertEquals(1999, dto.getYear());
    }

    @Test
    void movieDTOWithNullValues() {
        MovieDTO dto = new MovieDTO(null, null, null);
        assertNull(dto.getId());
        assertNull(dto.getTitle());
        assertNull(dto.getYear());
    }
}
