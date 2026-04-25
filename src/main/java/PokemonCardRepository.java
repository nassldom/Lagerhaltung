import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PokemonCardRepository {

    public void save(PokemonCard pokemonCard) {
        String sql = """
                INSERT INTO pokemon_cards (
                    article_id,
                    article_name,
                    article_price,
                    stock,
                    set_name,
                    release_year,
                    card_condition,
                    holo_type,
                    language,
                    first_edition
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, pokemonCard.getArticleID());
            statement.setString(2, pokemonCard.getArticleName());
            statement.setString(3, pokemonCard.getArticlePrice().toPlainString());
            statement.setInt(4, pokemonCard.getStock());
            statement.setString(5, pokemonCard.getSetName());
            statement.setInt(6, pokemonCard.getReleaseYear());
            statement.setString(7, pokemonCard.getCondition().name());
            statement.setString(8, pokemonCard.getHoloType().name());
            statement.setString(9, pokemonCard.getLanguage());
            statement.setInt(10, pokemonCard.isFirstEdition() ? 1 : 0);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Speichern der PokemonCard: " + e.getMessage(), e);
        }
    }

    public void saveOrUpdate(PokemonCard pokemonCard) {
        String sql = """
                INSERT INTO pokemon_cards (
                    article_id,
                    article_name,
                    article_price,
                    stock,
                    set_name,
                    release_year,
                    card_condition,
                    holo_type,
                    language,
                    first_edition
                ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT(article_id) DO UPDATE SET
                    article_name = excluded.article_name,
                    article_price = excluded.article_price,
                    stock = excluded.stock,
                    set_name = excluded.set_name,
                    release_year = excluded.release_year,
                    card_condition = excluded.card_condition,
                    holo_type = excluded.holo_type,
                    language = excluded.language,
                    first_edition = excluded.first_edition
                """;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, pokemonCard.getArticleID());
            statement.setString(2, pokemonCard.getArticleName());
            statement.setString(3, pokemonCard.getArticlePrice().toPlainString());
            statement.setInt(4, pokemonCard.getStock());
            statement.setString(5, pokemonCard.getSetName());
            statement.setInt(6, pokemonCard.getReleaseYear());
            statement.setString(7, pokemonCard.getCondition().name());
            statement.setString(8, pokemonCard.getHoloType().name());
            statement.setString(9, pokemonCard.getLanguage());
            statement.setInt(10, pokemonCard.isFirstEdition() ? 1 : 0);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Speichern/Aktualisieren der PokemonCard: " + e.getMessage(), e);
        }
    }

    public List<PokemonCard> findAll() {
        String sql = "SELECT * FROM pokemon_cards ORDER BY article_name";
        List<PokemonCard> pokemonCards = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                pokemonCards.add(mapRowToPokemonCard(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Laden aller PokemonCards: " + e.getMessage(), e);
        }

        return pokemonCards;
    }

    public PokemonCard findById(String articleID) {
        String sql = "SELECT * FROM pokemon_cards WHERE article_id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, articleID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRowToPokemonCard(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Laden der PokemonCard: " + e.getMessage(), e);
        }

        return null;
    }

    public boolean deleteById(String articleID) {
        String sql = "DELETE FROM pokemon_cards WHERE article_id = ?";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, articleID);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Löschen der PokemonCard: " + e.getMessage(), e);
        }
    }

    public void deleteAll() {
        String sql = "DELETE FROM pokemon_cards";

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Löschen aller PokemonCards: " + e.getMessage(), e);
        }
    }

    private PokemonCard mapRowToPokemonCard(ResultSet resultSet) throws SQLException {
        String articleName = resultSet.getString("article_name");
        String articleID = resultSet.getString("article_id");
        BigDecimal articlePrice = new BigDecimal(resultSet.getString("article_price"));
        int stock = resultSet.getInt("stock");
        String setName = resultSet.getString("set_name");
        int releaseYear = resultSet.getInt("release_year");
        CardCondition condition = CardCondition.valueOf(resultSet.getString("card_condition"));
        HoloType holoType = HoloType.valueOf(resultSet.getString("holo_type"));
        String language = resultSet.getString("language");
        boolean firstEdition = resultSet.getInt("first_edition") == 1;

        return new PokemonCard(
                articleName,
                articleID,
                articlePrice,
                stock,
                setName,
                releaseYear,
                condition,
                holoType,
                language,
                firstEdition
        );
    }
}