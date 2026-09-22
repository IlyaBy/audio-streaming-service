package web.app.api.models;


public record Track(Long id, String title, String artist, String album, Long duration, Integer year) {}

