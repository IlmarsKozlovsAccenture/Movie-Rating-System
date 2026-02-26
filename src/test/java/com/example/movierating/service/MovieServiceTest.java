package com.example.movierating.service;

import com.example.movierating.dto.MovieRequestDTO;
import com.example.movierating.dto.MovieResponseDTO;
import com.example.movierating.entity.Movie;
import com.example.movierating.exception.MovieAlreadyExistsException;
import com.example.movierating.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

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

        MovieRequestDTO dto = new MovieRequestDTO("X", 2020);
        MovieResponseDTO saved = service.createMovie(dto);

        assertNotNull(saved.getId());
        assertEquals("X", saved.getTitle());
        assertEquals(2020, saved.getYear());
        verify(repo, times(1)).save(any(Movie.class));
    }

    @Test
    void createMovie_throwsExceptionWhenDatabaseConstraintViolated() {
        when(repo.save(any(Movie.class)))
                .thenThrow(new DataIntegrityViolationException("Unique constraint violated on title and year"));

        MovieRequestDTO dto = new MovieRequestDTO("X", 2020);

        assertThrows(MovieAlreadyExistsException.class, () -> service.createMovie(dto));
        verify(repo, times(1)).save(any(Movie.class));
    }

    @Test
    void createMovie_multipleMovies() {
        Movie m1 = new Movie("Movie1", 2020);
        m1.setId(1L);
        Movie m2 = new Movie("Movie2", 2021);
        m2.setId(2L);

        when(repo.save(any(Movie.class))).thenReturn(m1).thenReturn(m2);

        MovieRequestDTO dto1 = new MovieRequestDTO("Movie1", 2020);
        MovieRequestDTO dto2 = new MovieRequestDTO("Movie2", 2021);

        MovieResponseDTO saved1 = service.createMovie(dto1);
        MovieResponseDTO saved2 = service.createMovie(dto2);

        assertEquals(1L, saved1.getId());
        assertEquals(2L, saved2.getId());
        verify(repo, times(2)).save(any(Movie.class));
    }

    @Test
    void listMovies_returnsDtos() {
        Movie m = new Movie("A", 2000);
        m.setId(2L);
        when(repo.findAll()).thenReturn(List.of(m));

        List<MovieResponseDTO> list = service.listMovies();
        assertEquals(1, list.size());
        assertEquals(2L, list.get(0).getId());
        assertEquals("A", list.get(0).getTitle());
    }

    @Test
    void listMovies_emptyList() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        List<MovieResponseDTO> list = service.listMovies();
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

        List<MovieResponseDTO> list = service.listMovies();
        assertEquals(3, list.size());
        assertEquals("Movie1", list.get(0).getTitle());
        assertEquals("Movie2", list.get(1).getTitle());
        assertEquals("Movie3", list.get(2).getTitle());
    }
}
