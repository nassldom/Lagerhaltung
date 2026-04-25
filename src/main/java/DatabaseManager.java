import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:pokemon_inventory.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        String sql = """
                CREATE TABLE IF NOT EXISTS pokemon_cards (
                    article_id TEXT PRIMARY KEY,
                    article_name TEXT NOT NULL,
                    article_price TEXT NOT NULL,
                    stock INTEGER NOT NULL,
                    set_name TEXT NOT NULL,
                    release_year INTEGER NOT NULL,
                    card_condition TEXT NOT NULL,
                    holo_type TEXT NOT NULL,
                    language TEXT NOT NULL,
                    first_edition INTEGER NOT NULL
                );
                """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Initialisieren der Datenbank: " + e.getMessage(), e);
        }
    }
}