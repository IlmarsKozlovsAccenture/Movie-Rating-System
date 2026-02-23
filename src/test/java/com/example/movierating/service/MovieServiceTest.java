package com.example.movierating.service;

import com.example.movierating.dto.MovieDTO;
import com.example.movierating.entity.Movie;
import com.example.movierating.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MovieServiceTest {
    private MovieRepository repo;
    private MovieService service;

    @BeforeEach
    void setUp() {
        repo = Mockito.mock(MovieRepository.class);
        service = new MovieService(repo);
    }

    @Test
    void createMovie_savesAndReturnsDto() {
        Movie m = new Movie("X", 2020);
        m.setId(1L);
        when(repo.save(any(Movie.class))).thenReturn(m);

        MovieDTO dto = new MovieDTO(null, "X", 2020);
        MovieDTO saved = service.createMovie(dto);

        assertNotNull(saved.getId());
        assertEquals("X", saved.getTitle());
        assertEquals(2020, saved.getYear());
        verify(repo, times(1)).save(any(Movie.class));
    }

    @Test
    void createMovie_multipleMovies() {
        Movie m1 = new Movie("Movie1", 2020);
        m1.setId(1L);
        Movie m2 = new Movie("Movie2", 2021);
        m2.setId(2L);

        when(repo.save(any(Movie.class))).thenReturn(m1).thenReturn(m2);

        MovieDTO dto1 = new MovieDTO(null, "Movie1", 2020);
        MovieDTO dto2 = new MovieDTO(null, "Movie2", 2021);

        MovieDTO saved1 = service.createMovie(dto1);
        MovieDTO saved2 = service.createMovie(dto2);

        assertEquals(1L, saved1.getId());
        assertEquals(2L, saved2.getId());
        verify(repo, times(2)).save(any(Movie.class));
    }

    @Test
    void listMovies_returnsDtos() {
        Movie m = new Movie("A", 2000);
        m.setId(2L);
        when(repo.findAll()).thenReturn(List.of(m));

        List<MovieDTO> list = service.listMovies();
        assertEquals(1, list.size());
        assertEquals(2L, list.get(0).getId());
        assertEquals("A", list.get(0).getTitle());
    }

    @Test
    void listMovies_emptyList() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        List<MovieDTO> list = service.listMovies();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void listMovies_multipleMovies() {
        Movie m1 = new Movie("Movie1", 2020);
        m1.setId(1L);
        Movie m2 = new Movie("Movie2", 2021);
        m2.setId(2L);
        Movie m3 = new Movie("Movie3", 2022);
        m3.setId(3L);

        when(repo.findAll()).thenReturn(List.of(m1, m2, m3));

        List<MovieDTO> list = service.listMovies();
        assertEquals(3, list.size());
        assertEquals("Movie1", list.get(0).getTitle());
        assertEquals("Movie2", list.get(1).getTitle());
        assertEquals("Movie3", list.get(2).getTitle());
    }
}
