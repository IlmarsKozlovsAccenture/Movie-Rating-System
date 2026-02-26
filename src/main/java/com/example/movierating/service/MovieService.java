package com.example.movierating.service;

import com.example.movierating.dto.MovieRequestDTO;
import com.example.movierating.dto.MovieResponseDTO;
import com.example.movierating.entity.Movie;
import com.example.movierating.repository.MovieRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public MovieResponseDTO createMovie(MovieRequestDTO dto) {
        try {
            Movie m = new Movie(dto.getTitle(), dto.getYear());
            Movie saved = repository.save(m);
            return new MovieResponseDTO(saved.getId(), saved.getTitle(), saved.getYear());
        } catch (DataIntegrityViolationException e) {
            throw new MovieAlreadyExistsException(
                    String.format("Movie with title '%s' and year %d already exists", 
                            dto.getTitle(), dto.getYear()));
        }
    }

    public List<MovieResponseDTO> listMovies() {
        return repository.findAll().stream()
                .map(m -> new MovieResponseDTO(m.getId(), m.getTitle(), m.getYear()))
                .collect(Collectors.toList());
    }
}
