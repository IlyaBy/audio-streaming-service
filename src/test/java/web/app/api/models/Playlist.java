package web.app.api.models;

import java.util.List;

public record Playlist(
        Long id,
        String name,
        String description,
        Boolean isPublic,
        Long userId,
        List<Track> tracks
) {
    public Playlist(String name, String description, Boolean isPublic, Long userId) {
        this(null, name, description, isPublic, userId, null);
    }
}
