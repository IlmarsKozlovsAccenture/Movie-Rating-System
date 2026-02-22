package com.example.movierating.service;

import com.example.movierating.dto.MovieDTO;
import com.example.movierating.entity.Movie;
import com.example.movierating.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

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
        verify(repo, times(1)).save(any(Movie.class));
    }

    @Test
    void listMovies_returnsDtos() {
        Movie m = new Movie("A", 2000);
        m.setId(2L);
        when(repo.findAll()).thenReturn(List.of(m));

        List<MovieDTO> list = service.listMovies();
        assertEquals(1, list.size());
        assertEquals(2L, list.get(0).getId());
    }
}
