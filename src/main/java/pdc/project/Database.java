package pdc.project;

import java.sql.SQLException;

public interface Database {
    void saveHighestScore(String userName, int levelID, int score) throws SQLException;
    int queryScores(String userName, int levelID) throws SQLException;
    void clear() throws SQLException;
}