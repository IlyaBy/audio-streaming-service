package web.app.api.tests;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import web.app.api.models.Playlist;


public class ApiTest extends BaseTest{

   @Test
   void scenario1CreatePlaylist() {
       Long userId = USER_SERVICE.getFirstUserId();
       String playlistName = "Scenario 1 - " + UUID.randomUUID();
       Playlist requestBody = new Playlist(playlistName, "Scenario 1 description", true, userId);
       Playlist createdPlaylist = PLAYLIST_SERVICE.createPlaylist(requestBody);

       playlistIdToDelete = createdPlaylist.id();

       assertEquals(playlistName, createdPlaylist.name());

       Playlist retrievedPlaylist = PLAYLIST_SERVICE.getPlaylistById(playlistIdToDelete);
       assertEquals(playlistName, retrievedPlaylist.name());
       assertEquals(userId, retrievedPlaylist.userId());
   }

    @Test
    void scenario2ModifyPlaylistAttributes() {
        Long userId = USER_SERVICE.getFirstUserId();
        Playlist created = PLAYLIST_SERVICE.createPlaylist(new Playlist("Init Name", "Init Desc", false, userId));

        playlistIdToDelete = created.id();

        String updatedName = "Updated " + UUID.randomUUID();
        Playlist updateRequest = new Playlist(updatedName, "Updated description", true, userId);

        Playlist updated = PLAYLIST_SERVICE.updatePlaylist(playlistIdToDelete, updateRequest);
        assertEquals(updatedName, updated.name());
        assertTrue(updated.isPublic());
    }
    @Test
    void scenario3AddTracksToPlaylist() {
        Long userId = USER_SERVICE.getFirstUserId();
        Long trackId = TRACK_SERVICE.getTrackIdByIndex(0);
        Playlist request = new Playlist("Scenario 3", "Desc", true, userId);

        Playlist created = PLAYLIST_SERVICE.createPlaylist(request);
        playlistIdToDelete = created.id();

        PLAYLIST_SERVICE.addTrackToPlaylist(playlistIdToDelete, trackId);

        Playlist retrieved = PLAYLIST_SERVICE.getPlaylistById(playlistIdToDelete);
        assertNotNull(retrieved.tracks(), "Track list should not be null");
        boolean hasTrackInGet = retrieved.tracks().stream().anyMatch(t -> t.id().equals(trackId));
        assertTrue(hasTrackInGet, "Track should be present in the retrieved playlist");
    }

    @Test
    void scenario4RemoveTrackFromPlaylist() {
        Long userId = USER_SERVICE.getFirstUserId();
        Long trackId1 = TRACK_SERVICE.getTrackIdByIndex(0);
        Long trackId2 = TRACK_SERVICE.getTrackIdByIndex(1);

        Playlist created = PLAYLIST_SERVICE.createPlaylist(new Playlist("Scenario 4", "Desc", true, userId));
        playlistIdToDelete = created.id();

        PLAYLIST_SERVICE.addTrackToPlaylist(playlistIdToDelete, trackId1);
        PLAYLIST_SERVICE.addTrackToPlaylist(playlistIdToDelete, trackId2);

        PLAYLIST_SERVICE.removeTrackFromPlaylist(playlistIdToDelete, trackId1);

        Playlist retrieved = PLAYLIST_SERVICE.getPlaylistById(playlistIdToDelete);
        assertNotNull(retrieved.tracks(), "Track list should not be null");

        boolean hasTrack1InGet = retrieved.tracks().stream().anyMatch(t -> t.id().equals(trackId1));
        boolean hasTrack2InGet = retrieved.tracks().stream().anyMatch(t -> t.id().equals(trackId2));

        assertFalse(hasTrack1InGet, "Removed track should not be present in the retrieved playlist");
        assertTrue(hasTrack2InGet, "The second track should still be present in the playlist");
    }

    @Test
    void scenario5DeletePlaylist() {
        Long userId = USER_SERVICE.getFirstUserId();
        Playlist created = PLAYLIST_SERVICE.createPlaylist(new Playlist("Scenario 5", "Desc", true, userId));
        Long id = created.id();

        PLAYLIST_SERVICE.deletePlaylist(id);

        assertThrows(AssertionError.class, () -> PLAYLIST_SERVICE.getPlaylistById(id));
    }
}

