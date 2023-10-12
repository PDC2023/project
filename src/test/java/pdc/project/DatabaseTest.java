package pdc.project;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private Database db;

    @BeforeEach
    void setUp() throws SQLException {
        db = new Database();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveHighestScoreAndQueryScores() throws SQLException {
        String userName = "Alice";
        int levelID = 1;
        int score = 100;

        db.saveHighestScore(userName, levelID, score);

        int queriedScore = db.queryScores(userName, levelID);
        assertEquals(score, queriedScore);

        int higherScore = 200;
        db.saveHighestScore(userName, levelID, higherScore);

        queriedScore = db.queryScores(userName, levelID);
        assertEquals(higherScore, queriedScore);
    }

    @Test
    void queryScoresWithNoExistingScore() throws SQLException {
        String userName = "Bob";
        int levelID = 2;

        int queriedScore = db.queryScores(userName, levelID);
        assertEquals(0, queriedScore);
    }
}
