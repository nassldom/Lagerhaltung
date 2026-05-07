/*
PokemonCardRepository ist die Klasse für den Datenbankzugriff auf Pokémon-Karten. Sie kapselt das Speichern, Updaten,
Laden, Suchen und Löschen und trennt damit Datenbanklogik sauber vom Rest deines Programms. mapRowToPokemonCard(...) ist
dabei eine typische Mapping-Methode, die aus einer Datenbankzeile wieder ein Java-Objekt erzeugt.

Wichtiger Hinweis
in aktueller Version ist saveOrUpdate(...) noch unvollständig. Der try-Block enthält nur das Öffnen der Verbindung
und des Statements, aber noch keine statement.set-(...)-Aufrufe und kein statement.executeUpdate(). Dadurch wird dort
aktuell weder gespeichert noch aktualisiert.
 */
import java.math.BigDecimal;
// Importiert BigDecimal.
// Wird verwendet, um den Kartenpreis exakt als Dezimalzahl darzustellen.

import java.sql.Connection;
// Importiert Connection.
// Repräsentiert eine Verbindung zur Datenbank.

import java.sql.PreparedStatement;
// Importiert PreparedStatement.
// Damit werden vorbereitete SQL-Befehle mit Platzhaltern (?) ausgeführt.

import java.sql.ResultSet;
// Importiert ResultSet.
// Enthält die Ergebnisse einer SELECT-Abfrage.

import java.sql.SQLException;
// Importiert SQLException.
// Diese Exception tritt bei Datenbankfehlern auf.

import java.util.ArrayList;
// Importiert ArrayList als konkrete Listenklasse.

import java.util.List;
// Importiert das List-Interface.

public class PokemonCardRepository {
// Repository-Klasse für den Datenbankzugriff auf PokemonCard-Objekte.
// Ein Repository kapselt typischerweise Speichern, Laden, Suchen und Löschen.

    public void save(PokemonCard pokemonCard) {
// Methode zum Speichern einer neuen PokemonCard in der Datenbank.

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
// SQL-Insert-Befehl als Textblock.
// Die Fragezeichen sind Platzhalter für die später gesetzten Werte.

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
// Öffnet eine Verbindung zur Datenbank
// und erstellt daraus ein PreparedStatement.
// try-with-resources sorgt dafür, dass beides automatisch geschlossen wird.

            statement.setString(1, pokemonCard.getArticleID());
// Setzt den ersten Platzhalter auf die Artikel-ID.

            statement.setString(2, pokemonCard.getArticleName());
// Setzt den zweiten Platzhalter auf den Artikelnamen.

            statement.setString(3, pokemonCard.getArticlePrice().toPlainString());
// Setzt den Preis als String.
// toPlainString() liefert den BigDecimal-Wert ohne wissenschaftliche Schreibweise.

            statement.setInt(4, pokemonCard.getStock());
// Setzt den Bestand.

            statement.setString(5, pokemonCard.getSetName());
// Setzt den Set namen.

            statement.setInt(6, pokemonCard.getReleaseYear());
// Setzt das Erscheinungsjahr.

            statement.setString(7, pokemonCard.getCondition().name());
// Setzt den Zustand als Enum-Name, z. B. "NEAR_MINT".

            statement.setString(8, pokemonCard.getHoloType().name());
// Setzt den HoloType als Enum-Name.

            statement.setString(9, pokemonCard.getLanguage());
// Setzt die Sprache.

            statement.setInt(10, pokemonCard.isFirstEdition() ? 1 : 0);
// Speichert firstEdition als 1 oder 0 in der Datenbank.
// Der ternäre Operator '?' : wählt je nach boolean den passenden Wert.

            statement.executeUpdate();
// Führt den INSERT-Befehl aus.
// executeUpdate() wird für INSERT, UPDATE und DELETE verwendet.

        } catch (SQLException e) {
// Fängt Datenbankfehler ab.

            throw new RuntimeException("Fehler beim Speichern der PokemonCard: " + e.getMessage(), e);
// Wandelt die SQLException in eine RuntimeException um
// und gibt eine verständliche Fehlermeldung weiter.
        }
    }

    public void saveOrUpdate(PokemonCard pokemonCard) {
// Methode zum Speichern oder Aktualisieren einer PokemonCard.
// Falls die article_id schon existiert, wird der Datensatz aktualisiert.

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
// SQL-UPSERT-Befehl.
// ON CONFLICT(article_id) bedeutet:
// Wenn die article_id schon existiert, dann werden die angegebenen Felder aktualisiert.

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
// Öffnet Verbindung und PreparedStatement.


// Achtung:
// In deiner aktuellen Version fehlt hier noch der eigentliche Inhalt.
// Es werden bisher keine Parameter gesetzt und executeUpdate() wird nicht aufgerufen.
// Deshalb macht diese Methode momentan noch nichts.

        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Speichern/Aktualisieren der PokemonCard: " + e.getMessage(), e);
        }
    }

    public List<PokemonCard> findAll() {
// Lädt alle PokemonCards aus der Datenbank.

        String sql = "SELECT * FROM pokemon_cards ORDER BY article_name";
// SQL-Abfrage: Wählt alle Datensätze aus und sortiert nach article_name.

        List<PokemonCard> pokemonCards = new ArrayList<>();
// Leere Liste für die geladenen PokemonCards.

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
// Öffnet Verbindung, PreparedStatement und führt die SELECT-Abfrage direkt aus.
// Das Ergebnis landet im ResultSet.

            while (resultSet.next()) {
// Geht Datensatz für Datensatz durch das Ergebnis.

                pokemonCards.add(mapRowToPokemonCard(resultSet));
// Wandelt die aktuelle Zeile in ein PokemonCard-Objekt um
// und fügt es der Liste hinzu.
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Laden aller PokemonCards: " + e.getMessage(), e);
        }

        return pokemonCards;
// Gibt die vollständige Liste zurück.
    }

    public PokemonCard findById(String articleID) {
// Sucht genau eine PokemonCard anhand ihrer article_id.

        String sql = "SELECT * FROM pokemon_cards WHERE article_id = ?";
// SQL-Abfrage mit Platzhalter für die ID.

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
// Öffnet Verbindung und PreparedStatement.

            statement.setString(1, articleID);
// Setzt den ersten Platzhalter auf die übergebene articleID.

            try (ResultSet resultSet = statement.executeQuery()) {
// Führt die SELECT-Abfrage aus.

                if (resultSet.next()) {
// Prüft, ob ein Datensatz gefunden wurde.

                    return mapRowToPokemonCard(resultSet);
// Wandelt die Zeile in ein PokemonCard-Objekt um und gibt es zurück.
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Laden der PokemonCard: " + e.getMessage(), e);
        }

        return null;
// Wenn nichts gefunden wurde, wird null zurückgegeben.
    }

    public boolean deleteById(String articleID) {
// Löscht eine PokemonCard anhand ihrer article_id.

        String sql = "DELETE FROM pokemon_cards WHERE article_id = ?";
// SQL-Befehl zum Löschen genau eines Datensatzes.

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, articleID);
// Setzt den Platzhalter auf die gewünschte ID.

            return statement.executeUpdate() > 0;
// Führt das DELETE aus.
// executeUpdate() gibt zurück, wie viele Zeilen betroffen waren.
// > 0 bedeutet: Es wurde mindestens ein Datensatz gelöscht.
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Löschen der PokemonCard: " + e.getMessage(), e);
        }
    }

    public void deleteAll() {
// Löscht alle PokemonCards aus der Datenbank.

        String sql = "DELETE FROM pokemon_cards";
// SQL-Befehl ohne WHERE-Klausel.
// Dadurch werden alle Datensätze der Tabelle gelöscht.

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
// Führt den Löschbefehl aus.
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Löschen aller PokemonCards: " + e.getMessage(), e);
        }
    }

    private PokemonCard mapRowToPokemonCard(ResultSet resultSet) throws SQLException {
// Hilfsmethode, die eine einzelne Zeile aus dem ResultSet
// in ein PokemonCard-Objekt umwandelt.

        String articleName = resultSet.getString("article_name");
// Liest den Artikelnamen aus der Spalte article_name.

        String articleID = resultSet.getString("article_id");
// Liest die Artikel-ID.

        BigDecimal articlePrice = new BigDecimal(resultSet.getString("article_price"));
// Liest den Preis als String und wandelt ihn in BigDecimal um.

        int stock = resultSet.getInt("stock");
// Liest den Bestand.

        String setName = resultSet.getString("set_name");
// Liest den Set namen.

        int releaseYear = resultSet.getInt("release_year");
// Liest das Erscheinungsjahr.

        CardCondition condition = CardCondition.valueOf(resultSet.getString("card_condition"));
// Liest den gespeicherten Enum-Namen und wandelt ihn zurück in CardCondition.

        HoloType holoType = HoloType.valueOf(resultSet.getString("holo_type"));
// Liest den HoloType aus der Datenbank und wandelt ihn in die Enum zurück.

        String language = resultSet.getString("language");
// Liest die Sprache.

        boolean firstEdition = resultSet.getInt("first_edition") == 1;
// Liest den Integer-Wert 1 oder 0 und wandelt ihn in boolean um.

        return new PokemonCard(
// Erstellt aus allen gelesenen Werten ein neues PokemonCard-Objekt.

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