package com.example.movierating.dto;

public class MovieResponseDTO {
    private Long id;
    private String title;
    private Integer year;

    public MovieResponseDTO() {}

    public MovieResponseDTO(Long id, String title, Integer year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}
