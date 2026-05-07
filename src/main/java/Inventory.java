/*
Die Klasse Inventory ist die zentrale Verwaltungsklasse für dein Lager. Sie speichert Artikel und Lagerbewegungen,
ermöglicht Suchen, Hinzufügen, Entfernen sowie Bestandsänderungen und kann alle Daten in Dateien speichern und wieder
daraus laden.

Wichtige Punkte:
-articles speichert alle Artikelobjekte.
-stockMovements speichert jede Ein- und Auslagerung.
-saveToFile() und loadFromFile() sind für Artikel.
-saveMovementsToFile() und loadMovementsFromFile() sind für Lagerbewegungen.

Durch instanceof kann dieselbe Liste unterschiedliche Unterklassen wie Article und PokemonCard enthalten.

Ein wichtiger Java-Punkt dabei ist das Prinzip der Vererbung mit Ober- und Unterklassen, wie es auch bei BaseArticle
und konkreten Klassen genutzt wird.
 */

import java.io.BufferedReader;
// Importiert BufferedReader zum effizienten Lesen von Textdateien.

import java.io.BufferedWriter;
// Importiert BufferedWriter zum effizienten Schreiben in Textdateien.

import java.io.FileReader;
// Importiert FileReader zum Lesen von Dateien.

import java.io.FileWriter;
// Importiert FileWriter zum Schreiben von Dateien.

import java.io.IOException;
// Importiert IOException für Ein-/Ausgabe-Fehler bei Dateien.

import java.math.BigDecimal;
// Importiert BigDecimal für exakte Dezimalzahlen, z. B. Preise.

import java.time.LocalDateTime;
// Importiert LocalDateTime für Datum und Uhrzeit ohne Zeitzone.

import java.time.format.DateTimeFormatter;
// Importiert DateTimeFormatter zum Formatieren und Parsen von Datum/Zeit.

import java.time.format.DateTimeParseException;
// Importiert DateTimeParseException für Fehler beim Einlesen von Datum/Zeit.

import java.util.ArrayList;
// Importiert ArrayList als konkrete Listen-Implementierung.

import java.util.List;
// Importiert das List-Interface.

public class Inventory {
// Definiert die Klasse Inventory.
// Diese Klasse verwaltet Artikel und Lagerbewegungen.

    private List<BaseArticle> articles;
// Liste aller Artikel im Inventar.
// BaseArticle als Oberklasse erlaubt verschiedene Artikeltypen in einer Liste.

    private List<StockMovement> stockMovements;
// Liste aller Lagerbewegungen (Zugänge und Abgänge).

    public Inventory() {
// Konstruktor der Klasse Inventory.

        this.articles = new ArrayList<>();
// Initialisiert die Artikelliste als leere ArrayList.

        this.stockMovements = new ArrayList<>();
// Initialisiert die Liste der Lagerbewegungen als leere ArrayList.
    }

    public void addArticle(BaseArticle article) {
// Methode zum Hinzufügen eines Artikels.

        if (article == null) {
// Prüft, ob der übergebene Artikel null ist.

            throw new IllegalArgumentException("Artikel darf nicht null sein.");
// Null ist ungültig, daher wird eine Ausnahme geworfen.
        }

        for (BaseArticle existingArticle : articles) {
// Schleife über alle bereits vorhandenen Artikel.

            if (existingArticle.getArticleID().equalsIgnoreCase(article.getArticleID())) {
// Prüft, ob schon ein Artikel mit derselben ID existiert.

                throw new IllegalArgumentException("Artikel mit dieser Nummer existiert bereits.");
// Doppelte IDs sind nicht erlaubt.
            }
        }

        articles.add(article);
// Fügt den Artikel zur Liste hinzu.
    }

    public List<BaseArticle> getAllArticles() {
// Gibt alle Artikel zurück.

        return articles;
// Rückgabe der Artikelliste.
    }

    public List<PokemonCard> getAllPokemonCards() {
// Gibt nur die Artikel zurück, die PokemonCard-Objekte sind.

        List<PokemonCard> pokemonCards = new ArrayList<>();
// Erstellt eine leere Liste nur für PokemonCard-Objekte.

        for (BaseArticle article : articles) {
// Durchläuft alle Artikel.

            if (article instanceof PokemonCard pokemonCard) {
// Prüft, ob article eine PokemonCard ist.
// Falls ja, wird es direkt als pokemonCard verfügbar gemacht.

                pokemonCards.add(pokemonCard);
// Fügt die PokemonCard zur Ergebnisliste hinzu.
            }
        }

        return pokemonCards;
// Gibt die Liste aller PokemonCards zurück.
    }

    public BaseArticle getArticleByNumber(String articleID) {
// Sucht einen Artikel anhand seiner Artikelnummer/ID.

        if (articleID == null || articleID.trim().isEmpty()) {
// Prüft, ob die ID null oder leer ist.

            throw new IllegalArgumentException("Artikelnummer darf nicht leer sein.");
// Leere IDs sind ungültig.
        }

        for (BaseArticle article : articles) {
// Schleife über alle Artikel.

            if (article.getArticleID().equalsIgnoreCase(articleID.trim())) {
// Prüft, ob die Artikel-ID übereinstimmt, ohne Groß-/Kleinschreibung zu beachten.

                return article;
// Gibt den gefundenen Artikel zurück.
            }
        }

        return null;
// Falls nichts gefunden wurde, wird null zurückgegeben.
    }

    public BaseArticle getArticleByName(String articleName) {
// Sucht einen Artikel anhand seines Namens.

        if (articleName == null || articleName.trim().isEmpty()) {
// Prüft, ob der Name null oder leer ist.

            throw new IllegalArgumentException("Artikelname darf nicht leer sein.");
        }

        for (BaseArticle article : articles) {
// Schleife über alle Artikel.

            if (article.getArticleName().equalsIgnoreCase(articleName.trim())) {
// Prüft, ob der Name übereinstimmt.

                return article;
// Gibt den passenden Artikel zurück.
            }
        }

        return null;
// Gibt null zurück, wenn kein passender Artikel gefunden wurde.
    }

    public boolean removeArticle(String articleID) {
// Entfernt einen Artikel anhand seiner ID.

        BaseArticle article = getArticleByNumber(articleID);
// Sucht zuerst den Artikel.

        if (article == null) {
// Prüft, ob der Artikel nicht gefunden wurde.

            return false;
// Gibt false zurück, da nichts entfernt werden konnte.
        }

        return articles.remove(article);
// Entfernt den Artikel aus der Liste.
// Gibt true zurück, wenn das Entfernen erfolgreich war.
    }

    public void increaseStock(String articleID, int amount) {
// Erhöht den Lagerbestand eines Artikels.

        BaseArticle article = getArticleByNumber(articleID);
// Sucht den Artikel über seine ID.

        if (article == null) {
// Prüft, ob kein Artikel gefunden wurde.

            throw new IllegalArgumentException("Artikel nicht gefunden.");
// Ohne Artikel kann kein Bestand erhöht werden.
        }

        article.increaseStock(amount);
// Erhöht den Bestand des Artikels.

        stockMovements.add(new StockMovement(
// Fügt zusätzlich eine Lagerbewegung zur Historie hinzu.

                article.getArticleID(),
// Übergibt die Artikel-ID an die Lagerbewegung.

                article.getArticleName(),
// Übergibt den Artikelnamen.

                MovementType.IN,
// Kennzeichnet die Bewegung als Zugang.

                amount
// Übergibt die Menge des Zugangs.
        ));
    }

    public void decreaseStock(String articleID, int amount) {
// Verringert den Lagerbestand eines Artikels.

        BaseArticle article = getArticleByNumber(articleID);
// Sucht den Artikel anhand der ID.

        if (article == null) {
// Prüft, ob kein Artikel gefunden wurde.

            throw new IllegalArgumentException("Artikel nicht gefunden.");
        }

        article.decreaseStock(amount);
// Verringert den Bestand des Artikels.

        stockMovements.add(new StockMovement(
// Fügt eine neue Lagerbewegung hinzu.

                article.getArticleID(),
                article.getArticleName(),
                MovementType.OUT,
// Kennzeichnet die Bewegung als Abgang.

                amount
        ));
    }

    public List<BaseArticle> getLowStockItems() {
// Gibt alle Artikel zurück, deren Bestand zu niedrig ist.

        List<BaseArticle> lowStockItems = new ArrayList<>();
// Erstellt eine leere Liste für Artikel mit niedrigem Bestand.

        for (BaseArticle article : articles) {
// Schleife über alle Artikel.

            if (article.hasLowStock()) {
// Prüft mit der jeweiligen Implementierung, ob der Bestand niedrig ist.

                lowStockItems.add(article);
// Fügt den Artikel der Liste hinzu.
            }
        }

        return lowStockItems;
// Gibt die Liste zurück.
    }

    public List<StockMovement> getAllMovements() {
// Gibt alle Lagerbewegungen zurück.

        return stockMovements;
    }

    public List<StockMovement> getMovementsByArticleID(String articleID) {
// Gibt alle Lagerbewegungen zu einer bestimmten Artikel-ID zurück.

        if (articleID == null || articleID.trim().isEmpty()) {
// Prüft die Eingabe.

            throw new IllegalArgumentException("Artikelnummer darf nicht leer sein.");
        }

        List<StockMovement> matchingMovements = new ArrayList<>();
// Ergebnisliste für passende Bewegungen.

        for (StockMovement movement : stockMovements) {
// Schleife über alle Lagerbewegungen.

            if (movement.getArticleID().equalsIgnoreCase(articleID.trim())) {
// Prüft, ob die Bewegung zur gesuchten Artikel-ID gehört.

                matchingMovements.add(movement);
// Fügt passende Bewegungen hinzu.
            }
        }

        return matchingMovements;
// Gibt die Trefferliste zurück.
    }

    public int getArticleCount() {
// Gibt die Anzahl der Artikel zurück.

        return articles.size();
// size() liefert die Anzahl der Elemente in der Liste.
    }

    public int getTotalStock() {
// Berechnet den gesamten Lagerbestand über alle Artikel.

        int totalStock = 0;
// Startwert für die Summe.

        for (BaseArticle article : articles) {
// Schleife über alle Artikel.

            totalStock += article.getStock();
// Addiert den Bestand jedes Artikels zur Gesamtsumme.
        }

        return totalStock;
// Gibt die Gesamtsumme zurück.
    }

    public int getLowStockCount() {
// Gibt die Anzahl der Artikel mit niedrigem Bestand zurück.

        return getLowStockItems().size();
// Nutzt die bereits vorhandene Methode und gibt deren Größe zurück.
    }

    public void saveToFile(String fileName) throws IOException {
// Speichert alle Artikel in eine Datei.
// throws IOException: Fehler beim Schreiben werden weitergegeben.

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
// Öffnet die Datei zum Schreiben.
// try-with-resources sorgt dafür, dass der Writer automatisch geschlossen wird.

            for (BaseArticle article : articles) {
// Schleife über alle Artikel.

                if (article instanceof Article normalArticle) {
// Prüft, ob der Artikel ein normales Article-Objekt ist.

                    writer.write(
                            "ARTICLE;" +
// Schreibt zuerst den Typ, damit man ihn später wieder erkennen kann.

                                    normalArticle.getArticleName() + ";" +
// Schreibt den Artikelnamen.

                                    normalArticle.getArticleID() + ";" +
// Schreibt die Artikel-ID.

                                    normalArticle.getArticlePrice() + ";" +
// Schreibt den Preis.

                                    normalArticle.getStock() + ";" +
// Schreibt den Bestand.

                                    normalArticle.getMinimumStock()
// Schreibt den Mindestbestand.
                    );
                } else if (article instanceof PokemonCard pokemonCard) {
// Prüft, ob der Artikel eine PokemonCard ist.

                    writer.write(
                            "POKEMONCARD;" +
// Typkennung für PokemonCard.

                                    pokemonCard.getArticleName() + ";" +
                                    pokemonCard.getArticleID() + ";" +
                                    pokemonCard.getArticlePrice() + ";" +
                                    pokemonCard.getStock() + ";" +
                                    pokemonCard.getSetName() + ";" +
                                    pokemonCard.getReleaseYear() + ";" +
                                    pokemonCard.getCondition().name() + ";" +
// Speichert den Enum-Namen des Zustands.

                                    pokemonCard.getHoloType().name() + ";" +
// Speichert den Enum-Namen des HoloTyps.

                                    pokemonCard.getLanguage() + ";" +
                                    pokemonCard.isFirstEdition()
// Speichert true oder false für Erstauflage.
                    );
                }

                writer.newLine();
// Fügt nach jedem Artikel einen Zeilenumbruch hinzu.
            }
        }
    }

    public void loadFromFile(String fileName) throws IOException {
// Lädt Artikel aus einer Datei.
// Bestehende Artikel werden nach erfolgreichem Laden ersetzt.

        List<BaseArticle> loadedArticles = new ArrayList<>();
// Temporäre Liste für eingelesene Artikel.

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
// Öffnet die Datei zum Lesen.

            String line;
// Variable für die jeweils gelesene Zeile.

            while ((line = reader.readLine()) != null) {
// Liest zeilenweise, bis keine Zeile mehr vorhanden ist.

                if (line.trim().isEmpty()) {
// Überspringt leere Zeilen.

                    continue;
                }

                String[] parts = line.split(";");
// Teilt die Zeile an jedem Semikolon auf.

                if (parts[0].equals("ARTICLE")) {
// Prüft, ob die Zeile einen normalen Artikel beschreibt.

                    if (parts.length != 6) {
// Prüft, ob genau 6 Teile vorhanden sind.

                        throw new IllegalArgumentException("Ungültiges Artikelformat in Datei: " + line);
// Fehler bei falschem Dateiformat.
                    }

                    String articleName = parts[1].trim();
// Liest den Artikelnamen aus.

                    String articleID = parts[2].trim();
// Liest die Artikel-ID aus.

                    BigDecimal articlePrice = new BigDecimal(parts[3].trim());
// Wandelt den Preis in BigDecimal um.

                    int stock = Integer.parseInt(parts[4].trim());
// Wandelt den Bestand in int um.

                    int minimumStock = Integer.parseInt(parts[5].trim());
// Wandelt den Mindestbestand in int um.

                    loadedArticles.add(new Article(articleName, articleID, articlePrice, stock, minimumStock));
// Erstellt ein neues Article-Objekt und fügt es zur Liste hinzu.

                } else if (parts[0].equals("POKEMONCARD")) {
// Prüft, ob die Zeile eine PokemonCard beschreibt.

                    if (parts.length != 11) {
// PokemonCard braucht 11 Teile.

                        throw new IllegalArgumentException("Ungültiges PokemonCard-Format in Datei: " + line);
                    }

                    String articleName = parts[1].trim();
                    String articleID = parts[2].trim();
                    BigDecimal articlePrice = new BigDecimal(parts[3].trim());
                    int stock = Integer.parseInt(parts[4].trim());
                    String setName = parts[5].trim();
                    int releaseYear = Integer.parseInt(parts[6].trim());
                    CardCondition condition = CardCondition.valueOf(parts[7].trim());
// Liest den Enum-Wert für den Kartenzustand.

                    HoloType holoType = HoloType.valueOf(parts[8].trim());
// Liest den Enum-Wert für den HoloType.

                    String language = parts[9].trim();
                    boolean firstEdition = Boolean.parseBoolean(parts[10].trim());
// Wandelt den Text in true oder false um.

                    loadedArticles.add(new PokemonCard(
// Erstellt eine neue PokemonCard aus den gelesenen Daten.

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
                    ));
                } else {
// Falls weder ARTICLE noch POKEMONCARD erkannt wurde.

                    throw new IllegalArgumentException("Unbekannter Artikeltyp in Datei: " + line);
                }
            }
        }

        this.articles = loadedArticles;
// Ersetzt die aktuelle Artikelliste erst nach erfolgreichem Laden.
    }

    public void saveMovementsToFile(String fileName) throws IOException {
// Speichert alle Lagerbewegungen in eine Datei.

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
// Öffnet die Datei zum Schreiben.

            for (StockMovement movement : stockMovements) {
// Schleife über alle Lagerbewegungen.

                writer.write(movement.toFileString());
// Schreibt jede Lagerbewegung als formatierte Zeile in die Datei.

                writer.newLine();
// Danach Zeilenumbruch.
            }
        }
    }

    public void loadMovementsFromFile(String fileName) throws IOException {
// Lädt Lagerbewegungen aus einer Datei.

        List<StockMovement> loadedMovements = new ArrayList<>();
// Temporäre Liste für eingelesene Lagerbewegungen.

        DateTimeFormatter formatter = StockMovement.getFileFormatter();
// Holt das Datumsformat, das für Datei-Ein-/Ausgabe verwendet wird.

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
// Öffnet die Datei zum Lesen.

            String line;

            while ((line = reader.readLine()) != null) {
// Liest die Datei zeilenweise.

                if (line.trim().isEmpty()) {
// Überspringt leere Zeilen.

                    continue;
                }

                String[] parts = line.split(";");
// Zerlegt die Zeile anhand des Trennzeichens.

                if (parts.length != 5) {
// Eine Lagerbewegung muss genau 5 Teile enthalten.

                    throw new IllegalArgumentException("Ungültiges Bewegungsformat in Datei: " + line);
                }

                try {
// Innerer try-Block, um Fehler beim Parsen einzelner Werte abzufangen.

                    LocalDateTime timestamp = LocalDateTime.parse(parts[0].trim(), formatter);
// Liest und parst den Zeitstempel.

                    String articleID = parts[1].trim();
// Liest die Artikel-ID.

                    String articleName = parts[2].trim();
// Liest den Artikelnamen.

                    MovementType movementType = MovementType.valueOf(parts[3].trim());
// Liest den Enum-Wert für die Bewegungsart.

                    int amount = Integer.parseInt(parts[4].trim());
// Liest die Menge als int.

                    loadedMovements.add(new StockMovement(
// Erstellt ein neues StockMovement-Objekt und fügt es hinzu.

                            timestamp,
                            articleID,
                            articleName,
                            movementType,
                            amount
                    ));
                } catch (DateTimeParseException | IllegalArgumentException e) {
// Fängt Fehler beim Parsen von Datum, Enum oder Zahlen ab.

                    throw new IllegalArgumentException("Fehlerhafte Bewegungsdaten in Datei: " + line);
// Gibt eine verständliche Fehlermeldung mit der betroffenen Zeile aus.
                }
            }
        }

        this.stockMovements = loadedMovements;
// Ersetzt die aktuelle Liste der Lagerbewegungen nach erfolgreichem Laden.
    }
}