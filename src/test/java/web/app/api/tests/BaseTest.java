package web.app.api.tests;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import web.app.api.services.PlaylistService;
import web.app.api.services.TrackService;
import web.app.api.services.UserService;

public class BaseTest {

    protected static final Logger log = LogManager.getLogger(BaseTest.class);
    protected static final PlaylistService PLAYLIST_SERVICE = new PlaylistService();
    protected static final UserService USER_SERVICE = new UserService();
    protected static final TrackService TRACK_SERVICE = new TrackService();

    protected Long playlistIdToDelete = null;

    @BeforeAll
    static void setUp() {
        log.info("Test run started. Connecting to http://localhost:8888");
        RestAssured.baseURI = "http://localhost:8888";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterEach
    void tearDown() {

        if (playlistIdToDelete != null) {
            try {
                log.info("Backend automatic clean up hsa started. Deleting Playlist ID: {}", playlistIdToDelete);
                PLAYLIST_SERVICE.deletePlaylist(playlistIdToDelete);
            } catch (AssertionError | Exception e) {
                log.warn("Playlist ID {} does not need in clean up or had been deleted in test: {}", playlistIdToDelete, e.getMessage());
            }
            playlistIdToDelete = null;
        }
    }
}
