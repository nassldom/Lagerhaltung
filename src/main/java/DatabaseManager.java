/*
DatabaseManager bietet eine statische Verbindung zur SQLite‑Datenbank und eine Methode, um die Tabelle
pokemon_cards anzulegen, falls sie noch nicht existiert. Die Tabelle speichert alle relevanten Felder deiner
Pokémon‑Karten (Artikel‑ID, Preis, Zustand, Set, Jahr usw.).
 */

import java.sql.Connection;
// Importiert die Connection‑Schnittstelle für JDBC‑Datenbankverbindungen.
// Wird für die Verbindung zur Datenbank verwendet.

import java.sql.DriverManager;
// Importiert DriverManager, um eine Datenbankverbindung über eine URL zu erhalten.

import java.sql.SQLException;
// Importiert die Checked‑Exception für SQL/Fehler beim Datenbankzugriff.

import java.sql.Statement;
// Importiert die Statement‑Schnittstelle,
// um SQL‑Befehle (z.B. CREATE, INSERT, UPDATE) auszuführen.

public class DatabaseManager {

// Öffentliche Klasse, die Hilfsmethoden zum Zugriff und zur Initialisierung
// einer SQLite‑Datenbank für deinen Pokémon‑Karten‑Bestand bereitstellt.

    private static final String DB_URL = "jdbc:sqlite:pokemon_inventory.db";
// Statische, unveränderliche Zeichenkette für die Datenbank‑URL.
// Verwendet SQLite und speichert die DB-Datei als pokemon_inventory.db.

    public static Connection getConnection() throws SQLException {
// Statische Methode, die eine neue Connection zur Datenbank zurückgibt.
// throws SQLException: Die Methode kann eine SQL‑Ausnahme auslösen.

        return DriverManager.getConnection(DB_URL);
// Öffnet eine Verbindung zur SQLite‑Datenbank mithilfe der URL.
    }

    public static void initializeDatabase() {
// Statische Hilfsmethode, um die Datenbanktabelle pokemon_cards anzulegen
// (falls sie noch nicht existiert).

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
// SQL‑Befehl als String.
// CREATE TABLE IF NOT EXISTS legt die Tabelle nur an, wenn sie noch nicht existiert.
// Primärschlüssel: article_id.
// Alle Felder sind NOT NULL, also Pflichtfelder.

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
// Versuch, eine Verbindung zur Datenbank zu öffnen
// und ein Statement‑Objekt zu erstellen (try‑with‑resources).
// Die Ressourcen (Connection, Statement) werden automatisch geschlossen.

            statement.execute(sql);
// Führt den SQL‑Befehl aus, also die CREATE‑TABLE‑Anweisung.

        } catch (SQLException e) {
// Fängt alle SQL‑Fehler ab, z.B. wenn Datei nicht zugänglich ist.

            throw new RuntimeException("Fehler beim Initialisieren der Datenbank: " + e.getMessage(), e);
// Übersetzt die SQLException in eine ungeprüfte RuntimeException,
// um sie im Code einfacher nutzen zu können und weitere Informationen zu liefern.
        }
    }
}