package web.app.api.services;
import web.app.api.endpoints.UrlRouts;
import web.app.api.models.Playlist;
import web.app.api.models.TrackReference;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class PlaylistService extends BaseService {
    public Playlist createPlaylist(Playlist playlistRequest) {
        return given().spec(getRequestSpec())
                .body(playlistRequest)
                .when().post(UrlRouts.PLAYLISTS)
                .then().statusCode(201)
                .extract().as(Playlist.class);
    }

    public Playlist getPlaylistById(Long playlistId) {
        return given().spec(getRequestSpec())
                .pathParam("id", playlistId)
                .when().get(UrlRouts.PLAYLIST_BY_ID)
                .then().statusCode(200)
                .extract().as(Playlist.class);
    }

    public Playlist updatePlaylist(Long playlistId, Playlist playlistRequest) {
        return given().spec(getRequestSpec())
                .pathParam("id", playlistId)
                .body(playlistRequest)
                .when().put(UrlRouts.PLAYLIST_BY_ID)
                .then().statusCode(200)
                .extract().as(Playlist.class);
    }

    public void addTrackToPlaylist(Long playlistId, Long trackId) {
        given().spec(getRequestSpec())
                .pathParam("id", playlistId)
                .body(new TrackReference(trackId))
                .when().post(UrlRouts.ADD_TRACK)
                .then().statusCode(anyOf(is(200), is(201)));
    }

    public void removeTrackFromPlaylist(Long playlistId, Long trackId) {
        given().spec(getRequestSpec())
                .pathParam("id", playlistId)
                .body(new TrackReference(trackId))
                .when().delete(UrlRouts.REMOVE_TRACK)
                .then().statusCode(anyOf(is(200), is(204)));
    }

    public void deletePlaylist(Long playlistId) {
        given().spec(getRequestSpec())
                .pathParam("id", playlistId)
                .when().delete(UrlRouts.PLAYLIST_BY_ID)
                .then().statusCode(anyOf(is(200), is(204)));
    }
}
