package com.example.movierating.dto;

public class RatingResponseDTO {
    private Long id;
    private Long movieId;
    private int score;
    private String comment;

    public RatingResponseDTO() {}

    public RatingResponseDTO(Long id, Long movieId, int score, String comment) {
        this.id = id;
        this.movieId = movieId;
        this.score = score;
        this.comment = comment;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
