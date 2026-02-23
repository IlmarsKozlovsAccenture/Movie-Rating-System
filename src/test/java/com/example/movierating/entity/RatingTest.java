package com.example.movierating.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingTest {

    @Test
    void createRating_defaultConstructor() {
        Rating rating = new Rating();
        assertNull(rating.getId());
        assertNull(rating.getMovieId());
    }

    @Test
    void createRating_withConstructor() {
        Rating rating = new Rating(1L, 5, "Excellent");
        assertNull(rating.getId());
        assertEquals(1L, rating.getMovieId());
        assertEquals(5, rating.getScore());
        assertEquals("Excellent", rating.getComment());
    }

    @Test
    void ratingSettersAndGetters() {
        Rating rating = new Rating();
        rating.setId(10L);
        rating.setMovieId(1L);
        rating.setScore(4);
        rating.setComment("Good movie");

        assertEquals(10L, rating.getId());
        assertEquals(1L, rating.getMovieId());
        assertEquals(4, rating.getScore());
        assertEquals("Good movie", rating.getComment());
    }

    @Test
    void ratingEqualsAndHashCode() {
        Rating rating1 = new Rating(1L, 5, "Excellent");
        rating1.setId(10L);
        Rating rating2 = new Rating(1L, 2, "Bad");
        rating2.setId(10L);

        assertEquals(rating1, rating2);
        assertEquals(rating1.hashCode(), rating2.hashCode());
    }

    @Test
    void ratingNotEqualsWithDifferentId() {
        Rating rating1 = new Rating(1L, 5, "Excellent");
        rating1.setId(10L);
        Rating rating2 = new Rating(1L, 5, "Excellent");
        rating2.setId(11L);

        assertNotEquals(rating1, rating2);
    }

    @Test
    void ratingDifferentScores() {
        Rating rating1 = new Rating(1L, 5, "a");
        Rating rating2 = new Rating(1L, 3, "b");

        assertEquals(5, rating1.getScore());
        assertEquals(3, rating2.getScore());
    }
}
