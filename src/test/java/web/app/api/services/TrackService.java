package web.app.api.services;
import web.app.api.endpoints.UrlRouts;
import web.app.api.models.Track;
import static io.restassured.RestAssured.given;
import java.util.List;

public class TrackService extends BaseService {

    public List<Track> getAllTracks() {
        return given().spec(getRequestSpec())
                .when().get(UrlRouts.TRACKS)
                .then().extract().jsonPath().getList("tracks", Track.class);
    }

    public Long getTrackIdByIndex(int index) {
        List<Track> tracks = getAllTracks();
        if (tracks.size() <= index) {
            throw new IllegalArgumentException("Not enough tracks available in the system for index " + index);
        }
        return tracks.get(index).id();
    }
}
