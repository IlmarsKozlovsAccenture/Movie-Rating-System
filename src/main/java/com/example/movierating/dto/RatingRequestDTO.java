package com.example.movierating.dto;

public class RatingRequestDTO {
    private Long movieId;
    private int score;
    private String comment;

    public RatingRequestDTO() {}

    public RatingRequestDTO(Long movieId, int score, String comment) {
        this.movieId = movieId;
        this.score = score;
        this.comment = comment;
    }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
}
