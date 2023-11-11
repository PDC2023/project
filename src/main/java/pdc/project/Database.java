package pdc.project;

import java.sql.SQLException;
/**
 * The {@code Database} interface defines methods for interacting with a database
 * to store and retrieve game-related data such as high scores and configuration settings.
 * Implementations of this interface provide specific database-related functionality.
 */
public interface Database {

    /**
     * Saves the highest score achieved by a user for a specific level in the database.
     *
     * @param userName The username of the player.
     * @param levelID  The identifier of the level.
     * @param score    The highest score achieved by the player for the level.
     * @throws SQLException If there is an error while saving the score in the database.
     */
    void saveHighestScore(String userName, int levelID, int score) throws SQLException;

    /**
     * Queries the highest score achieved by a user for a specific level from the database.
     *
     * @param userName The username of the player.
     * @param levelID  The identifier of the level.
     * @return The highest score achieved by the player for the level.
     * @throws SQLException If there is an error while querying the score from the database.
     */
    int queryScores(String userName, int levelID) throws SQLException;

    /**
     * Clears the database, removing all stored data.
     *
     * @throws SQLException If there is an error while clearing the database.
     */
    void clear() throws SQLException;

    /**
     * Retrieves a configuration value from the database based on the provided key.
     * If the key is not found, the defaultValue is returned.
     *
     * @param key          The key of the configuration value.
     * @param defaultValue The default value to be returned if the key is not found.
     * @return The value associated with the key or the defaultValue if the key is not found.
     * @throws SQLException If there is an error while querying the configuration value from the database.
     */
    String getConfigValue(String key, String defaultValue) throws SQLException;

    /**
     * Saves a configuration key-value pair in the database.
     *
     * @param key   The key of the configuration value.
     * @param value The value to be associated with the key.
     * @throws SQLException If there is an error while saving the configuration in the database.
     */
    void saveConfig(String key, String value) throws SQLException;
}
