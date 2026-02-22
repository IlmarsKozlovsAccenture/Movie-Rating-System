package com.example.movierating.service;

import com.example.movierating.dto.MovieDTO;
import com.example.movierating.entity.Movie;
import com.example.movierating.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public MovieDTO createMovie(MovieDTO dto) {
        Movie m = new Movie(dto.getTitle(), dto.getYear());
        Movie saved = repository.save(m);
        return new MovieDTO(saved.getId(), saved.getTitle(), saved.getYear());
    }

    public List<MovieDTO> listMovies() {
        return repository.findAll().stream()
                .map(m -> new MovieDTO(m.getId(), m.getTitle(), m.getYear()))
                .collect(Collectors.toList());
    }
}
