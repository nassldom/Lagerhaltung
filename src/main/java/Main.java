import java.io.IOException;
// Importiert IOException.
// Diese Exception wird für Fehler bei Datei-Ein-/Ausgabe benötigt.

import java.math.BigDecimal;
// Importiert BigDecimal.
// Wird für Preise verwendet, damit Dezimalzahlen exakt gespeichert werden.

import java.util.List;
// Importiert das List-Interface.
// Wird für Listen von Artikeln, PokemonCards und Lagerbewegungen genutzt.

import java.util.Scanner;
// Importiert Scanner.
// Scanner liest Benutzereingaben aus der Konsole ein.

public class Main {
// Öffentliche Klasse Main.
// Diese Klasse enthält den Startpunkt des Programms.

    private static final String ARTICLE_TABLE_LINE =
            "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
// Konstante für eine lange Trennlinie in der Artikeltabelle.
// static final bedeutet: eine feste, unveränderbare Klassenkonstante.

    private static final String POKEMON_TABLE_LINE =
            "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
// Konstante für die Trennlinie der PokemonCard-Tabelle.

    private static final String MOVEMENT_TABLE_LINE =
            "----------------------------------------------------------------------------------------------------------------";
// Konstante für die Trennlinie der Bewegungstabelle.

    public static void main(String[] args) {
// Startmethode des Programms.
// public: von überall aufrufbar
// static: gehört zur Klasse, nicht zu einem Objekt
// void: gibt nichts zurück

        DatabaseManager.initializeDatabase();
// Initialisiert beim Start die Datenbank.
// Falls die Tabelle noch nicht existiert, wird sie angelegt.

        Scanner scanner = new Scanner(System.in);
// Erstellt einen Scanner für Konsoleneingaben.

        Inventory inventory = new Inventory();
// Erstellt ein neues Inventory-Objekt zur Verwaltung der Artikel.

        PokemonCardRepository pokemonCardRepository = new PokemonCardRepository();
// Erstellt ein Repository-Objekt für den Datenbankzugriff auf PokemonCards.

        boolean running = true;
// Steuervariable für die Hauptschleife.
// Solange running true ist, läuft das Programm weiter.

        while (running) {
// Hauptschleife des Programms.
// Das Menü wird wiederholt angezeigt, bis der Benutzer beendet.

            printMenu();
// Gibt das Hauptmenü auf der Konsole aus.

            String choice = scanner.nextLine();
// Liest die Benutzerauswahl als String ein.

            switch (choice) {
// switch prüft den Wert von choice einmal
// und führt den passenden case-Block aus. [web: 12][web: 13]

                case "1":
// Wenn der Benutzer "1" eingibt:

                    createStandardArticle(scanner, inventory);
// Ruft die Methode zum Erstellen eines Standardartikels auf.

                    break;
// Beendet diesen case, damit nicht in den nächsten case "durchgefallen" wird. [web: 12]

                case "2":
                    createPokemonCard(scanner, inventory);
                    break;

                case "3":
                    showAllArticles(inventory);
                    break;

                case "4":
                    showAllPokemonCards(inventory);
                    break;

                case "5":
                    searchArticleByID(scanner, inventory);
                    break;

                case "6":
                    searchArticleByName(scanner, inventory);
                    break;

                case "7":
                    bookIncomingStock(scanner, inventory);
                    break;

                case "8":
                    bookOutgoingStock(scanner, inventory);
                    break;

                case "9":
                    showLowStockItems(inventory);
                    break;

                case "10":
                    deleteArticle(scanner, inventory);
                    break;

                case "11":
                    saveArticles(scanner, inventory);
                    break;

                case "12":
                    loadArticles(scanner, inventory);
                    break;

                case "13":
                    showAllMovements(inventory);
                    break;

                case "14":
                    showMovementsByArticle(scanner, inventory);
                    break;

                case "15":
                    saveMovements(scanner, inventory);
                    break;

                case "16":
                    loadMovements(scanner, inventory);
                    break;

                case "17":
                    showStatistics(inventory);
                    break;

                case "18":
                    savePokemonCardsToDatabase(inventory, pokemonCardRepository);
                    break;

                case "19":
                    loadPokemonCardsFromDatabase(inventory, pokemonCardRepository);
                    break;

                case "20":
                    showPokemonCardsFromDatabase(pokemonCardRepository);
                    break;

                case "21":
                    clearPokemonCardsDatabase(scanner, pokemonCardRepository);
                    break;

                case "0":
// Bei Eingabe "0" soll das Programm beendet werden.

                    running = false;
// Setzt die Schleifenbedingung auf false.
// Dadurch endet die while-Schleife.

                    System.out.println("Programm beendet.");
// Gibt eine Abschlussmeldung aus.

                    break;

                default:
// Wird ausgeführt, wenn keine Eingabe zu einem case passt.

                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
// Fehlermeldung bei falscher Menüeingabe.
            }

            System.out.println();
// Gibt eine Leerzeile aus, damit die Konsole übersichtlicher bleibt.
        }

        scanner.close();
// Schließt den Scanner nach Programmende.
// Ressourcen sollten sauber geschlossen werden.
    }

    private static void printMenu() {
// Private Hilfsmethode zum Anzeigen des Menüs.
// private: nur innerhalb dieser Klasse nutzbar.

        System.out.println("===== Lagerverwaltung =====");
// Überschrift des Menüs.

        System.out.println("1 - Standardartikel anlegen");
        System.out.println("2 - PokemonCard anlegen");
        System.out.println("3 - Alle Artikel anzeigen");
        System.out.println("4 - Nur PokemonCards anzeigen");
        System.out.println("5 - Artikel nach Nummer suchen");
        System.out.println("6 - Artikel nach Namen suchen");
        System.out.println("7 - Zugang buchen");
        System.out.println("8 - Abgang buchen");
        System.out.println("9 - Kritische Bestände anzeigen");
        System.out.println("10 - Artikel löschen");
        System.out.println("11 - Artikel speichern");
        System.out.println("12 - Artikel laden");
        System.out.println("13 - Gesamte Buchungshistorie anzeigen");
        System.out.println("14 - Buchungshistorie zu Artikel anzeigen");
        System.out.println("15 - Buchungshistorie speichern");
        System.out.println("16 - Buchungshistorie laden");
        System.out.println("17 - Lagerstatistik anzeigen");
        System.out.println("18 - PokemonCards in DB speichern");
        System.out.println("19 - PokemonCards aus DB laden");
        System.out.println("20 - PokemonCards direkt aus DB anzeigen");
        System.out.println("21 - PokemonCard-DB leeren");
// Gibt alle Menüoptionen aus.

        System.out.println("0 - Beenden");
// Menüpunkt zum Beenden.

        System.out.print("Auswahl: ");
// Gibt den Eingaben prompt aus.
// print statt println, damit die Eingabe in derselben Zeile erfolgen kann.
    }

    private static void showStatistics(Inventory inventory) {
// Zeigt einfache Lagerstatistiken an.

        System.out.println("----- Lagerstatistik -----");
// Überschrift.

        System.out.println("Anzahl Artikel: " + inventory.getArticleCount());
// Gibt die Anzahl aller Artikel aus.

        System.out.println("Gesamtbestand: " + inventory.getTotalStock());
// Gibt den gesamten Lagerbestand aus.

        System.out.println("Kritische Artikel: " + inventory.getLowStockCount());
// Gibt aus, wie viele Artikel einen kritischen Bestand haben.
    }

    private static void createStandardArticle(Scanner scanner, Inventory inventory) {
// Erstellt einen normalen Artikel über Benutzereingaben.

        try {
// try-Block: Hier wird Code ausgeführt, der Fehler auslösen kann.

            String articleName = InputHelper.readNonEmptyString(scanner, "Artikelname: ");
// Liest einen nicht leeren Artikelnamen ein.

            String articleID = InputHelper.readNonEmptyString(scanner, "Artikelnummer: ");
// Liest eine nicht leere Artikelnummer ein.

            BigDecimal articlePrice = InputHelper.readNonNegativeBigDecimal(scanner, "Artikelpreis: ");
// Liest einen nicht negativen Preis ein.

            int stock = InputHelper.readNonNegativeInt(scanner, "Lagerbestand: ");
// Liest einen nicht negativen Lagerbestand ein.

            int minimumStock = InputHelper.readNonNegativeInt(scanner, "Mindestlagerbestand: ");
// Liest einen nicht negativen Mindestlagerbestand ein.

            Article article = new Article(articleName, articleID, articlePrice, stock, minimumStock);
// Erstellt ein neues Article-Objekt mit den eingegebenen Werten.

            inventory.addArticle(article);
// Fügt den Artikel dem Inventory hinzu.

            System.out.println("Artikel erfolgreich hinzugefügt:");
// Erfolgsmeldung.

            printSingleArticle(article);
// Gibt den neu angelegten Artikel in Tabellenform aus.

        } catch (IllegalArgumentException e) {
// Fängt ungültige Eingaben oder fachliche Fehler ab.

            System.out.println("Fehler: " + e.getMessage());
// Gibt die Fehlermeldung aus. try/catch dient dazu,
// Exceptions kontrolliert zu behandeln. [web: 17][web: 20]
        }
    }

    private static void createPokemonCard(Scanner scanner, Inventory inventory) {
// Erstellt eine PokemonCard über Benutzereingaben.

        try {
            String articleName = InputHelper.readNonEmptyString(scanner, "Kartenname: ");
            String articleID = InputHelper.readNonEmptyString(scanner, "Set nummer: ");
            BigDecimal articlePrice = InputHelper.readNonNegativeBigDecimal(scanner, "Preis: ");
            int stock = InputHelper.readNonNegativeInt(scanner, "Bestand: ");
            String setName = InputHelper.readNonEmptyString(scanner, "Set name: ");
            int releaseYear = InputHelper.readNonNegativeInt(scanner, "Erscheinungsjahr: ");
// Liest die Grunddaten der Karte ein.

            printAvailableConditions();
// Gibt alle möglichen Zustände aus.

            CardCondition condition = readCardCondition(scanner);
// Liest den Zustand ein, bis eine gültige Eingabe gemacht wird.

            printAvailableHoloTypes();
// Gibt alle möglichen HoloTypes aus.

            HoloType holoType = readHoloType(scanner);
// Liest einen gültigen HoloType ein.

            String language = InputHelper.readNonEmptyString(scanner, "Sprache: ");
// Liest die Sprache ein.

            boolean firstEdition = readYesNo(scanner, "First Edition (ja/nein): ");
// Liest ja/nein ein und wandelt das in true/false um.

            PokemonCard pokemonCard = new PokemonCard(
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
// Erstellt ein neues PokemonCard-Objekt.

            inventory.addArticle(pokemonCard);
// Fügt die Karte dem Inventory hinzu.

            System.out.println("PokemonCard erfolgreich hinzugefügt:");
            printSingleArticle(pokemonCard);
// Gibt die Karte direkt aus.

        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private static void printAvailableConditions() {
// Gibt alle verfügbaren Kartenzustände aus.

        System.out.println("Verfügbare Zustände:");
// Überschrift.

        for (CardCondition condition : CardCondition.values()) {
// Schleife über alle Enum-Werte von CardCondition.

            System.out.println("- " + condition.getFormattedDisplay());
// Gibt jeden Zustand formatiert aus.
        }
    }

    private static CardCondition readCardCondition(Scanner scanner) {
// Liest einen gültigen CardCondition-Wert ein.

        while (true) {
// Wiederholt die Eingabe, bis sie gültig ist.

            String input = InputHelper.readNonEmptyString(scanner, "Zustand eingeben: ");
// Liest den Text ein.

            try {
                return CardCondition.fromInput(input);
// Versucht, aus dem Text einen Enum-Wert zu erzeugen.
// Bei Erfolg wird der Wert sofort zurückgegeben.
            } catch (IllegalArgumentException e) {
                System.out.println("Ungültiger Zustand. Bitte erneut eingeben.");
// Bei ungültigem Zustand wird erneut gefragt.
            }
        }
    }

    private static void printAvailableHoloTypes() {
// Gibt alle möglichen HoloTypes aus.

        System.out.println("Verfügbare HoloType-Werte:");

        for (HoloType holoType : HoloType.values()) {
// Schleife über alle Enum-Werte von HoloType.

            System.out.println("- " + holoType.getDisplayName());
// Gibt den Anzeigenamen jedes HoloTypes aus.
        }
    }

    private static HoloType readHoloType(Scanner scanner) {
// Liest einen gültigen HoloType ein.

        while (true) {
            String input = InputHelper.readNonEmptyString(scanner, "HoloType eingeben: ");

            try {
                return HoloType.fromInput(input);
// Versucht, den Text in einen HoloType umzuwandeln.
            } catch (IllegalArgumentException e) {
                System.out.println("Ungültiger HoloType. Bitte erneut eingeben.");
            }
        }
    }

    private static boolean readYesNo(Scanner scanner, String prompt) {
// Liest eine Ja/Nein-Eingabe ein und gibt boolean zurück.

        while (true) {
// Wiederholung bis gültige Eingabe.

            String input = InputHelper.readNonEmptyString(scanner, prompt).trim().toLowerCase();
// Liest die Eingabe, entfernt Leerzeichen und wandelt in Kleinbuchstaben um.

            if (input.equals("ja") || input.equals("j")) {
// Prüft auf "ja" oder "j".

                return true;
// Ja entspricht true.
            }

            if (input.equals("nein") || input.equals("n")) {
// Prüft auf "nein" oder "n".

                return false;
// Nein entspricht false.
            }

            System.out.println("Bitte 'ja' oder 'nein' eingeben.");
// Fehlermeldung bei anderer Eingabe.
        }
    }

    private static void showAllArticles(Inventory inventory) {
// Zeigt alle Artikel an.

        List<BaseArticle> allArticles = inventory.getAllArticles();
// Holt alle Artikel aus dem Inventory.

        if (allArticles.isEmpty()) {
// Prüft, ob die Liste leer ist.

            System.out.println("Keine Artikel vorhanden.");
        } else {
            System.out.println("Alle Artikel:");
            printArticleTable(allArticles);
// Gibt die Artikelliste in Tabellenform aus.
        }
    }

    private static void showAllPokemonCards(Inventory inventory) {
// Zeigt nur PokemonCards an.

        List<PokemonCard> pokemonCards = inventory.getAllPokemonCards();
// Holt nur PokemonCard-Objekte.

        if (pokemonCards.isEmpty()) {
            System.out.println("Keine PokemonCards vorhanden.");
        } else {
            System.out.println("Alle PokemonCards:");
            printPokemonCardTable(pokemonCards);
        }
    }

    private static void searchArticleByID(Scanner scanner, Inventory inventory) {
// Sucht einen Artikel anhand seiner ID.

        String searchID = InputHelper.readNonEmptyString(scanner, "Artikelnummer eingeben: ");
// Liest die gesuchte ID ein.

        BaseArticle article = inventory.getArticleByNumber(searchID);
// Sucht den Artikel im Inventory.

        if (article != null) {
// Prüft, ob etwas gefunden wurde.

            System.out.println("Gefundener Artikel:");
            printSingleArticle(article);
        } else {
            System.out.println("Kein Artikel mit dieser Nummer gefunden.");
        }
    }

    private static void searchArticleByName(Scanner scanner, Inventory inventory) {
// Sucht einen Artikel anhand seines Namens.

        String searchName = InputHelper.readNonEmptyString(scanner, "Artikelname eingeben: ");
        BaseArticle article = inventory.getArticleByName(searchName);

        if (article != null) {
            System.out.println("Gefundener Artikel:");
            printSingleArticle(article);
        } else {
            System.out.println("Kein Artikel mit diesem Namen gefunden.");
        }
    }

    private static void bookIncomingStock(Scanner scanner, Inventory inventory) {
// Bucht einen Lagerzugang.

        try {
            String articleID = InputHelper.readNonEmptyString(scanner, "Artikelnummer für Zugang: ");
// Liest die Artikel-ID ein.

            int amount = InputHelper.readPositiveInt(scanner, "Zugangsmenge eingeben: ");
// Liest eine positive Menge ein.

            inventory.increaseStock(articleID, amount);
// Erhöht den Bestand des Artikels.

            System.out.println("Zugang erfolgreich gebucht:");
            printSingleArticle(inventory.getArticleByNumber(articleID));
// Gibt den aktualisierten Artikel aus.
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private static void bookOutgoingStock(Scanner scanner, Inventory inventory) {
// Bucht einen Lagerabgang.

        try {
            String articleID = InputHelper.readNonEmptyString(scanner, "Artikelnummer für Abgang: ");
            int amount = InputHelper.readPositiveInt(scanner, "Abgangsmenge eingeben: ");

            inventory.decreaseStock(articleID, amount);
// Verringert den Bestand.

            System.out.println("Abgang erfolgreich gebucht:");
            printSingleArticle(inventory.getArticleByNumber(articleID));
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private static void showLowStockItems(Inventory inventory) {
// Zeigt Artikel mit kritischem Bestand.

        List<BaseArticle> lowStockItems = inventory.getLowStockItems();
// Holt alle Artikel, deren Bestand als kritisch gilt.

        if (lowStockItems.isEmpty()) {
            System.out.println("Keine kritischen Bestände vorhanden.");
        } else {
            System.out.println("Kritische Bestände:");
            printArticleTable(lowStockItems);
        }
    }

    private static void printArticleTable(List<BaseArticle> articles) {
// Gibt eine Liste von Artikeln als formatierte Tabelle aus.

        System.out.println(ARTICLE_TABLE_LINE);
// Gibt die obere Trennlinie aus.

        System.out.printf(
                "%-15s %-15s %-25s %-12s %-10s %-15s %-15s %-12s %-20s %-15s %-12s %-15s%n",
                "Typ", "Artikelnummer", "Artikelname", "Preis", "Bestand", "Mindestbestand",
                "Set", "Jahr", "Zustand", "HoloType", "Sprache", "1st Edition"
        );
// printf formatiert Ausgaben mit Platzhaltern.
// %-15s bedeutet: String, linksbündig, 15 Zeichen breit.
// %n steht bei printf für einen Zeilenumbruch. [web: 16][web: 22]

        System.out.println(ARTICLE_TABLE_LINE);
// Gibt die nächste Trennlinie aus.

        for (BaseArticle article : articles) {
// Schleife über alle Artikel in der Liste.

            if (article instanceof Article normalArticle) {
// Prüft, ob es ein normaler Artikel ist.

                System.out.printf(
                        "%-15s %-15s %-25s %-12s %-10d %-15d %-15s %-12s %-20s %-15s %-12s %-15s%n",
                        normalArticle.getArticleType(),
                        normalArticle.getArticleID(),
                        normalArticle.getArticleName(),
                        normalArticle.getArticlePrice(),
                        normalArticle.getStock(),
                        normalArticle.getMinimumStock(),
                        "-",
                        "-",
                        "-",
                        "-",
                        "-",
                        "-"
                );
// Gibt die Daten des normalen Artikels aus.
// Für PokemonCard-spezifische Felder werden "-" eingesetzt.
            } else if (article instanceof PokemonCard pokemonCard) {
// Prüft, ob es eine PokemonCard ist.

                System.out.printf(
                        "%-15s %-15s %-25s %-12s %-10d %-15s %-15s %-12d %-20s %-15s %-12s %-15s%n",
                        pokemonCard.getArticleType(),
                        pokemonCard.getArticleID(),
                        pokemonCard.getArticleName(),
                        pokemonCard.getArticlePrice(),
                        pokemonCard.getStock(),
                        "-",
                        pokemonCard.getSetName(),
                        pokemonCard.getReleaseYear(),
                        pokemonCard.getCondition(),
                        pokemonCard.getHoloType(),
                        pokemonCard.getLanguage(),
                        pokemonCard.isFirstEdition() ? "Ja" : "Nein"
                );
// Gibt die Daten einer PokemonCard aus.
// Der ternäre Operator '?' : wählt zwischen "Ja" und "Nein".
            }
        }

        System.out.println(ARTICLE_TABLE_LINE);
// Untere Trennlinie der Tabelle.
    }

    private static void printPokemonCardTable(List<PokemonCard> pokemonCards) {
// Gibt nur PokemonCards in Tabellenform aus.

        System.out.println(POKEMON_TABLE_LINE);

        System.out.printf(
                "%-15s %-25s %-12s %-10s %-15s %-12s %-20s %-15s %-12s %-15s%n",
                "Set nummer", "Kartenname", "Preis", "Bestand", "Set", "Jahr",
                "Zustand", "HoloType", "Sprache", "1st Edition"
        );

        System.out.println(POKEMON_TABLE_LINE);

        for (PokemonCard pokemonCard : pokemonCards) {
// Schleife über alle PokemonCards.

            System.out.printf(
                    "%-15s %-25s %-12s %-10d %-15s %-12d %-20s %-15s %-12s %-15s%n",
                    pokemonCard.getArticleID(),
                    pokemonCard.getArticleName(),
                    pokemonCard.getArticlePrice(),
                    pokemonCard.getStock(),
                    pokemonCard.getSetName(),
                    pokemonCard.getReleaseYear(),
                    pokemonCard.getCondition(),
                    pokemonCard.getHoloType(),
                    pokemonCard.getLanguage(),
                    pokemonCard.isFirstEdition() ? "Ja" : "Nein"
            );
        }

        System.out.println(POKEMON_TABLE_LINE);
    }

    private static void printSingleArticle(BaseArticle article) {
// Gibt genau einen Artikel als Ein-Zeilen-Tabelle aus.

        System.out.println(ARTICLE_TABLE_LINE);

        System.out.printf(
                "%-15s %-15s %-25s %-12s %-10s %-15s %-15s %-12s %-20s %-15s %-12s %-15s%n",
                "Typ", "Artikelnummer", "Artikelname", "Preis", "Bestand", "Mindestbestand",
                "Set", "Jahr", "Zustand", "HoloType", "Sprache", "1st Edition"
        );

        System.out.println(ARTICLE_TABLE_LINE);


        System.out.println(ARTICLE_TABLE_LINE);
    }

    private static void printMovementTable(List<StockMovement> movements) {
// Gibt Lagerbewegungen als Tabelle aus.

        System.out.println(MOVEMENT_TABLE_LINE);

        System.out.printf("%-20s %-15s %-25s %-15s %-10s%n",
                "Zeitstempel", "Artikelnummer", "Artikelname", "Bewegung", "Menge");
// Tabellenkopf für Bewegungen.

        System.out.println(MOVEMENT_TABLE_LINE);

        for (StockMovement movement : movements) {
// Schleife über alle Bewegungen.

            System.out.printf("%-20s %-15s %-25s %-15s %-10d%n",
                    formatTimestamp(movement.getTimestamp()),
                    movement.getArticleID(),
                    movement.getArticleName(),
                    movement.getMovementType(),
                    movement.getAmount());
// Gibt jede Bewegung als formatierte Tabellenzeile aus.
        }

        System.out.println(MOVEMENT_TABLE_LINE);
    }

    private static String formatTimestamp(java.time.LocalDateTime timestamp) {
// Formatiert einen Zeitstempel als lesbaren String.

        java.time.format.DateTimeFormatter formatter =
                java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
// Erzeugt ein Datumsformat wie 07.05.2026 13:15:00.

        return timestamp.format(formatter);
// Formatiert den Zeitstempel mit diesem Muster und gibt ihn zurück.
    }

    private static void deleteArticle(Scanner scanner, Inventory inventory) {
// Löscht einen Artikel anhand seiner ID.

        String articleID = InputHelper.readNonEmptyString(scanner, "Artikelnummer zum Löschen eingeben: ");
// Liest die ID des zu löschenden Artikels ein.

        boolean removed = inventory.removeArticle(articleID);
// Versucht, den Artikel zu entfernen.

        if (removed) {
            System.out.println("Artikel wurde gelöscht.");
        } else {
            System.out.println("Kein Artikel mit dieser Nummer gefunden.");
        }
    }

    private static void saveArticles(Scanner scanner, Inventory inventory) {
// Speichert alle Artikel in eine Datei.

        String fileName = InputHelper.readNonEmptyString(scanner, "Dateiname für Artikel eingeben (z. B. articles.txt): ");
// Liest den Dateinamen ein.

        try {
            inventory.saveToFile(fileName);
// Schreibt die Artikeldaten in die Datei.

            System.out.println("Artikel wurden erfolgreich gespeichert.");
        } catch (IOException e) {
// Fängt Datei-Fehler ab.

            System.out.println("Fehler beim Speichern der Artikel: " + e.getMessage());
        }
    }

    private static void loadArticles(Scanner scanner, Inventory inventory) {
// Lädt Artikel aus einer Datei.

        String fileName = InputHelper.readNonEmptyString(scanner, "Dateiname für Artikel eingeben (z. B. articles.txt): ");

        try {
            inventory.loadFromFile(fileName);
// Liest die Datei und baut die Artikel daraus wieder auf.

            System.out.println("Artikel wurden erfolgreich geladen.");
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Artikel: " + e.getMessage());
        } catch (IllegalArgumentException e) {
// Zusätzlicher catch für fachlich falsche Daten in der Datei.

            System.out.println("Fehler in den Artikeldaten: " + e.getMessage());
        }
    }

    private static void showAllMovements(Inventory inventory) {
// Zeigt die komplette Buchungshistorie an.

        List<StockMovement> allMovements = inventory.getAllMovements();

        if (allMovements.isEmpty()) {
            System.out.println("Noch keine Buchungen vorhanden.");
        } else {
            System.out.println("Gesamte Buchungshistorie:");
            printMovementTable(allMovements);
        }
    }

    private static void showMovementsByArticle(Scanner scanner, Inventory inventory) {
// Zeigt nur Bewegungen zu einer bestimmten Artikelnummer.

        String articleID = InputHelper.readNonEmptyString(scanner, "Artikelnummer für Historie eingeben: ");

        List<StockMovement> articleMovements = inventory.getMovementsByArticleID(articleID);
// Holt alle Bewegungen dieses Artikels.

        if (articleMovements.isEmpty()) {
            System.out.println("Keine Buchungen für diesen Artikel vorhanden.");
        } else {
            System.out.println("Buchungshistorie für Artikel " + articleID + ":");
            printMovementTable(articleMovements);
        }
    }

    private static void saveMovements(Scanner scanner, Inventory inventory) {
// Speichert die Buchungshistorie in eine Datei.

        String fileName = InputHelper.readNonEmptyString(scanner, "Dateiname für Historie eingeben (z. B. movements.txt): ");

        try {
            inventory.saveMovementsToFile(fileName);
            System.out.println("Buchungshistorie wurde erfolgreich gespeichert.");
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Historie: " + e.getMessage());
        }
    }

    private static void loadMovements(Scanner scanner, Inventory inventory) {
// Lädt die Buchungshistorie aus einer Datei.

        String fileName = InputHelper.readNonEmptyString(scanner, "Dateiname für Historie eingeben (z. B. movements.txt): ");

        try {
            inventory.loadMovementsFromFile(fileName);
            System.out.println("Buchungshistorie wurde erfolgreich geladen.");
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Historie: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler in den Historien daten: " + e.getMessage());
        }
    }

    private static void savePokemonCardsToDatabase(Inventory inventory, PokemonCardRepository repository) {
// Speichert alle PokemonCards aus dem aktuellen Inventory in die Datenbank.

        List<PokemonCard> pokemonCards = inventory.getAllPokemonCards();
// Holt alle PokemonCards aus dem Inventory.

        if (pokemonCards.isEmpty()) {
// Wenn keine da sind, wird sofort beendet.

            System.out.println("Keine PokemonCards im aktuellen Inventory vorhanden.");
            return;
// return beendet die Methode sofort.
        }

        int savedCount = 0;
// Zähler für gespeicherte Karten.

        for (PokemonCard pokemonCard : pokemonCards) {
// Schleife über alle Karten.

            repository.saveOrUpdate(pokemonCard);
// Speichert die Karte oder aktualisiert sie in der Datenbank.

            savedCount++;
// Erhöht den Zähler.
        }

        System.out.println(savedCount + " PokemonCards wurden in der Datenbank gespeichert.");
// Gibt aus, wie viele Karten gespeichert wurden.
    }

    private static void loadPokemonCardsFromDatabase(Inventory inventory, PokemonCardRepository repository) {
// Lädt PokemonCards aus der Datenbank ins aktuelle Inventory.

        List<PokemonCard> dbCards = repository.findAll();
// Holt alle Karten aus der Datenbank.

        if (dbCards.isEmpty()) {
            System.out.println("Keine PokemonCards in der Datenbank vorhanden.");
            return;
        }

        int loadedCount = 0;
// Zählt erfolgreich geladene Karten.

        int skippedCount = 0;
// Zählt übersprungene Karten, z. B. bei doppelter ID.

        for (PokemonCard pokemonCard : dbCards) {
            try {
                inventory.addArticle(pokemonCard);
// Versucht, jede Karte ins Inventory zu übernehmen.

                loadedCount++;
            } catch (IllegalArgumentException e) {
// Falls die Karte schon existiert oder ungültig ist, wird sie übersprungen.

                skippedCount++;
            }
        }

        System.out.println(loadedCount + " PokemonCards wurden aus der Datenbank ins Inventory geladen.");

        if (skippedCount > 0) {
            System.out.println(skippedCount + " Karten wurden übersprungen, weil sie bereits im Inventory vorhanden waren.");
        }
    }

    private static void showPokemonCardsFromDatabase(PokemonCardRepository repository) {
// Zeigt PokemonCards direkt aus der Datenbank an.

        List<PokemonCard> dbCards = repository.findAll();

        if (dbCards.isEmpty()) {
            System.out.println("Keine PokemonCards in der Datenbank vorhanden.");
        } else {
            System.out.println("PokemonCards direkt aus der Datenbank:");
            printPokemonCardTable(dbCards);
        }
    }

    private static void clearPokemonCardsDatabase(Scanner scanner, PokemonCardRepository repository) {
// Löscht alle PokemonCards aus der Datenbank nach Bestätigung.

        boolean confirm = readYesNo(scanner, "Wirklich alle PokemonCards aus der DB löschen? (ja/nein): ");
// Fragt den Benutzer nach einer Bestätigung.

        if (!confirm) {
// !confirm bedeutet: wenn confirm false ist.

            System.out.println("Löschen abgebrochen.");
            return;
// Bricht die Methode ab, wenn nicht bestätigt wurde.
        }

        repository.deleteAll();
// Löscht alle PokemonCards aus der Datenbank.

        System.out.println("Alle PokemonCards wurden aus der Datenbank gelöscht.");
// Erfolgsmeldung.
    }
}