package pdc.project;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseDerby implements Closeable, Database {
    private final Connection conn;

    public DatabaseDerby() throws SQLException {
        var DB_URL = "jdbc:derby:pdcDatabase;create=true";
        conn = DriverManager.getConnection(DB_URL);
        createHighscoresTableIfNotExists();
        createConfigTableIfNotExists();
    }

    private void createHighscoresTableIfNotExists() throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, "HIGHSCORES", new String[]{"TABLE"});
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

    private void createConfigTableIfNotExists() throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, "GLOBAL_CONFIG", new String[]{"TABLE"});
        if (!resultSet.next()) {
            String createTableSQL = "CREATE TABLE GLOBAL_CONFIG ("
                    + "ConfigKey VARCHAR(255) PRIMARY KEY, "
                    + "ConfigValue VARCHAR(255))";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
            }
        }
    }

    @Override
    public String getConfigValue(String key, String defaultValue) throws SQLException {
        String queryConfigSQL = "SELECT ConfigValue FROM GLOBAL_CONFIG WHERE ConfigKey = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(queryConfigSQL)) {
            pstmt.setString(1, key);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("ConfigValue");
                } else {
                    return defaultValue;
                }
            }
        }
    }

    @Override
    public void saveConfig(String key, String value) throws SQLException {
        String checkKeySQL = "SELECT ConfigKey FROM GLOBAL_CONFIG WHERE ConfigKey = ?";

        try (PreparedStatement pstmtCheck = conn.prepareStatement(checkKeySQL)) {
            pstmtCheck.setString(1, key);
            try (ResultSet rs = pstmtCheck.executeQuery()) {
                if (rs.next()) {
                    String updateSQL = "UPDATE GLOBAL_CONFIG SET ConfigValue = ? WHERE ConfigKey = ?";
                    try (PreparedStatement pstmtUpdate = conn.prepareStatement(updateSQL)) {
                        pstmtUpdate.setString(1, value);
                        pstmtUpdate.setString(2, key);
                        pstmtUpdate.executeUpdate();
                    }
                } else {
                    String insertSQL = "INSERT INTO GLOBAL_CONFIG (ConfigKey, ConfigValue) VALUES (?, ?)";
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(insertSQL)) {
                        pstmtInsert.setString(1, key);
                        pstmtInsert.setString(2, value);
                        pstmtInsert.executeUpdate();
                    }
                }
            }
        }
    }

    @Override
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

    @Override
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

    @Override
    public void clear() throws SQLException {
        String clearTableSQL = "DELETE FROM HIGHSCORES";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(clearTableSQL);
        }
    }
}
