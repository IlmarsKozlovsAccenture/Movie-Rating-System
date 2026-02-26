package com.example.movierating.service;

import com.example.movierating.dto.RatingRequestDTO;
import com.example.movierating.dto.RatingResponseDTO;
import com.example.movierating.entity.Rating;
import com.example.movierating.exception.RatingAlreadyExistsException;
import com.example.movierating.repository.RatingRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private final RatingRepository repository;

    public RatingService(RatingRepository repository) {
        this.repository = repository;
    }

    public RatingResponseDTO addRating(RatingRequestDTO dto) {
        try {
            Rating r = new Rating(dto.getMovieId(), dto.getScore(), dto.getComment());
            Rating saved = repository.save(r);
            return new RatingResponseDTO(saved.getId(), saved.getMovieId(), saved.getScore(), saved.getComment());
        } catch (DataIntegrityViolationException e) {
            throw new RatingAlreadyExistsException(
                    String.format("Rating for movie %d already exists", 
                            dto.getMovieId()));
        }
    }

    public double averageForMovie(Long movieId) {
        List<Rating> list = repository.findByMovieId(movieId);
        if (list.isEmpty()) return 0.0;
        return list.stream().mapToInt(Rating::getScore).average().orElse(0.0);
    }

    public List<RatingResponseDTO> ratingsForMovie(Long movieId) {
        return repository.findByMovieId(movieId).stream()
                .map(r -> new RatingResponseDTO(r.getId(), r.getMovieId(), r.getScore(), r.getComment()))
                .collect(Collectors.toList());
    }
}
