package com.example.movierating.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieTest {

    @Test
    void createMovie_defaultConstructor() {
        Movie movie = new Movie();
        assertNull(movie.getId());
        assertNull(movie.getTitle());
        assertNull(movie.getYear());
    }

    @Test
    void createMovie_withConstructor() {
        Movie movie = new Movie("Inception", 2010);
        assertNull(movie.getId());
        assertEquals("Inception", movie.getTitle());
        assertEquals(2010, movie.getYear());
    }

    @Test
    void movieSettersAndGetters() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Matrix");
        movie.setYear(1999);

        assertEquals(1L, movie.getId());
        assertEquals("Matrix", movie.getTitle());
        assertEquals(1999, movie.getYear());
    }

    @Test
    void movieEqualsAndHashCode() {
        Movie movie1 = new Movie("Inception", 2010);
        movie1.setId(1L);
        Movie movie2 = new Movie("Matrix", 1999);
        movie2.setId(1L);

        assertEquals(movie1, movie2);
        assertEquals(movie1.hashCode(), movie2.hashCode());
    }

    @Test
    void movieNotEqualsWithDifferentId() {
        Movie movie1 = new Movie("Inception", 2010);
        movie1.setId(1L);
        Movie movie2 = new Movie("Inception", 2010);
        movie2.setId(2L);

        assertNotEquals(movie1, movie2);
        assertNotEquals(movie1.hashCode(), movie2.hashCode());
    }
}
