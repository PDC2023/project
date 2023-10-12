package pdc.project;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Database implements Closeable {
    private final Connection conn;

    public Database() throws SQLException {
        var DB_URL = "jdbc:derby:pdcDatabase;create=true";
        conn = DriverManager.getConnection(DB_URL);
        createTableIfNotExists(conn);
    }

    private static void createTableIfNotExists(Connection conn) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, "HIGHSCORES", new String[] {"TABLE"});
        if (!resultSet.next()) {
            String createTableSQL = "CREATE TABLE HIGHSCORES ("
                    + "UserName VARCHAR(255), "
                    + "LevelID INT, "
                    + "Timestamp VARCHAR(255), "
                    + "Score INT, "
                    + "PRIMARY KEY (UserName, LevelID))";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
            }
        }
    }
    public void saveHighestScore(String userName, int levelID, int score) throws SQLException {
        String checkRecordSQL = "SELECT Score FROM HIGHSCORES WHERE UserName = ? AND LevelID = ?";
        try (PreparedStatement pstmtCheck = conn.prepareStatement(checkRecordSQL)) {
            pstmtCheck.setString(1, userName);
            pstmtCheck.setInt(2, levelID);
            try (ResultSet rs = pstmtCheck.executeQuery()) {
                if (rs.next()) {
                    int existingScore = rs.getInt("Score");
                    if (score > existingScore) {
                        String updateScoreSQL = "UPDATE HIGHSCORES SET Score = ?, Timestamp = ? WHERE UserName = ? AND LevelID = ?";
                        try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateScoreSQL)) {
                            pstmtUpdate.setInt(1, score);
                            pstmtUpdate.setString(2, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                            pstmtUpdate.setString(3, userName);
                            pstmtUpdate.setInt(4, levelID);
                            pstmtUpdate.executeUpdate();
                        }
                    }
                } else {
                    String insertScoreSQL = "INSERT INTO HIGHSCORES (UserName, LevelID, Timestamp, Score) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(insertScoreSQL)) {
                        pstmtInsert.setString(1, userName);
                        pstmtInsert.setInt(2, levelID);
                        pstmtInsert.setString(3, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                        pstmtInsert.setInt(4, score);
                        pstmtInsert.executeUpdate();
                    }
                }
            }
        }
    }


    public int queryScores(String userName, int levelID) throws SQLException {
        String queryScoresSQL = "SELECT Score FROM HIGHSCORES WHERE UserName = ? AND LevelID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(queryScoresSQL)) {
            pstmt.setString(1, userName);
            pstmt.setInt(2, levelID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Score");
                } else {
                    return 0;
                }
            }
        }
    }

    @Override
    public void close() throws IOException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }
}
