package com.example.movierating.service;

import com.example.movierating.dto.RatingDTO;
import com.example.movierating.entity.Rating;
import com.example.movierating.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingService {
    private final RatingRepository repository;

    public RatingService(RatingRepository repository) {
        this.repository = repository;
    }

    public RatingDTO addRating(RatingDTO dto) {
        Rating r = new Rating(dto.getMovieId(), dto.getScore(), dto.getComment());
        Rating saved = repository.save(r);
        return new RatingDTO(saved.getId(), saved.getMovieId(), saved.getScore(), saved.getComment());
    }

    public double averageForMovie(Long movieId) {
        List<Rating> list = repository.findByMovieId(movieId);
        if (list.isEmpty()) return 0.0;
        return list.stream().mapToInt(Rating::getScore).average().orElse(0.0);
    }

    public List<RatingDTO> ratingsForMovie(Long movieId) {
        return repository.findByMovieId(movieId).stream()
                .map(r -> new RatingDTO(r.getId(), r.getMovieId(), r.getScore(), r.getComment()))
                .collect(Collectors.toList());
    }
}
