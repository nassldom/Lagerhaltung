import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String ARTICLE_TABLE_LINE =
            "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";

    private static final String POKEMON_TABLE_LINE =
            "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";

    private static final String MOVEMENT_TABLE_LINE =
            "----------------------------------------------------------------------------------------------------------------";

    public static void main(String[] args) {
        DatabaseManager.initializeDatabase();

        Scanner scanner = new Scanner(System.in);
        Inventory inventory = new Inventory();
        PokemonCardRepository pokemonCardRepository = new PokemonCardRepository();
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    createStandardArticle(scanner, inventory);
                    break;
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
                    running = false;
                    System.out.println("Programm beendet.");
                    break;
                default:
                    System.out.println("Ungültige Auswahl. Bitte erneut versuchen.");
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("===== Lagerverwaltung =====");
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
        System.out.println("0 - Beenden");
        System.out.print("Auswahl: ");
    }

    private static void showStatistics(Inventory inventory) {
        System.out.println("----- Lagerstatistik -----");
        System.out.println("Anzahl Artikel: " + inventory.getArticleCount());
        System.out.println("Gesamtbestand: " + inventory.getTotalStock());
        System.out.println("Kritische Artikel: " + inventory.getLowStockCount());
    }

    private static void createStandardArticle(Scanner scanner, Inventory inventory) {
        try {
            String articleName = InputHelper.readNonEmptyString(scanner, "Artikelname: ");
            String articleID = InputHelper.readNonEmptyString(scanner, "Artikelnummer: ");
            BigDecimal articlePrice = InputHelper.readNonNegativeBigDecimal(scanner, "Artikelpreis: ");
            int stock = InputHelper.readNonNegativeInt(scanner, "Lagerbestand: ");
            int minimumStock = InputHelper.readNonNegativeInt(scanner, "Mindestlagerbestand: ");

            Article article = new Article(articleName, articleID, articlePrice, stock, minimumStock);
            inventory.addArticle(article);

            System.out.println("Artikel erfolgreich hinzugefügt:");
            printSingleArticle(article);
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private static void createPokemonCard(Scanner scanner, Inventory inventory) {
        try {
            String articleName = InputHelper.readNonEmptyString(scanner, "Kartenname: ");
            String articleID = InputHelper.readNonEmptyString(scanner, "Setnummer: ");
            BigDecimal articlePrice = InputHelper.readNonNegativeBigDecimal(scanner, "Preis: ");
            int stock = InputHelper.readNonNegativeInt(scanner, "Bestand: ");
            String setName = InputHelper.readNonEmptyString(scanner, "Setname: ");
            int releaseYear = InputHelper.readNonNegativeInt(scanner, "Erscheinungsjahr: ");

            printAvailableConditions();
            CardCondition condition = readCardCondition(scanner);

            printAvailableHoloTypes();
            HoloType holoType = readHoloType(scanner);

            String language = InputHelper.readNonEmptyString(scanner, "Sprache: ");
            boolean firstEdition = readYesNo(scanner, "First Edition (ja/nein): ");

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

            inventory.addArticle(pokemonCard);

            System.out.println("PokemonCard erfolgreich hinzugefügt:");
            printSingleArticle(pokemonCard);
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private static void printAvailableConditions() {
        System.out.println("Verfügbare Zustände:");
        for (CardCondition condition : CardCondition.values()) {
            System.out.println("- " + condition.getFormattedDisplay());
        }
    }

    private static CardCondition readCardCondition(Scanner scanner) {
        while (true) {
            String input = InputHelper.readNonEmptyString(scanner, "Zustand eingeben: ");

            try {
                return CardCondition.fromInput(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Ungültiger Zustand. Bitte erneut eingeben.");
            }
        }
    }

    private static void printAvailableHoloTypes() {
        System.out.println("Verfügbare HoloType-Werte:");
        for (HoloType holoType : HoloType.values()) {
            System.out.println("- " + holoType.getDisplayName());
        }
    }

    private static HoloType readHoloType(Scanner scanner) {
        while (true) {
            String input = InputHelper.readNonEmptyString(scanner, "HoloType eingeben: ");

            try {
                return HoloType.fromInput(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Ungültiger HoloType. Bitte erneut eingeben.");
            }
        }
    }

    private static boolean readYesNo(Scanner scanner, String prompt) {
        while (true) {
            String input = InputHelper.readNonEmptyString(scanner, prompt).trim().toLowerCase();

            if (input.equals("ja") || input.equals("j")) {
                return true;
            }

            if (input.equals("nein") || input.equals("n")) {
                return false;
            }

            System.out.println("Bitte 'ja' oder 'nein' eingeben.");
        }
    }

    private static void showAllArticles(Inventory inventory) {
        List<BaseArticle> allArticles = inventory.getAllArticles();

        if (allArticles.isEmpty()) {
            System.out.println("Keine Artikel vorhanden.");
        } else {
            System.out.println("Alle Artikel:");
            printArticleTable(allArticles);
        }
    }

    private static void showAllPokemonCards(Inventory inventory) {
        List<PokemonCard> pokemonCards = inventory.getAllPokemonCards();

        if (pokemonCards.isEmpty()) {
            System.out.println("Keine PokemonCards vorhanden.");
        } else {
            System.out.println("Alle PokemonCards:");
            printPokemonCardTable(pokemonCards);
        }
    }

    private static void searchArticleByID(Scanner scanner, Inventory inventory) {
        String searchID = InputHelper.readNonEmptyString(scanner, "Artikelnummer eingeben: ");
        BaseArticle article = inventory.getArticleByNumber(searchID);

        if (article != null) {
            System.out.println("Gefundener Artikel:");
            printSingleArticle(article);
        } else {
            System.out.println("Kein Artikel mit dieser Nummer gefunden.");
        }
    }

    private static void searchArticleByName(Scanner scanner, Inventory inventory) {
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
        try {
            String articleID = InputHelper.readNonEmptyString(scanner, "Artikelnummer für Zugang: ");
            int amount = InputHelper.readPositiveInt(scanner, "Zugangsmenge eingeben: ");

            inventory.increaseStock(articleID, amount);

            System.out.println("Zugang erfolgreich gebucht:");
            printSingleArticle(inventory.getArticleByNumber(articleID));
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private static void bookOutgoingStock(Scanner scanner, Inventory inventory) {
        try {
            String articleID = InputHelper.readNonEmptyString(scanner, "Artikelnummer für Abgang: ");
            int amount = InputHelper.readPositiveInt(scanner, "Abgangsmenge eingeben: ");

            inventory.decreaseStock(articleID, amount);

            System.out.println("Abgang erfolgreich gebucht:");
            printSingleArticle(inventory.getArticleByNumber(articleID));
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler: " + e.getMessage());
        }
    }

    private static void showLowStockItems(Inventory inventory) {
        List<BaseArticle> lowStockItems = inventory.getLowStockItems();

        if (lowStockItems.isEmpty()) {
            System.out.println("Keine kritischen Bestände vorhanden.");
        } else {
            System.out.println("Kritische Bestände:");
            printArticleTable(lowStockItems);
        }
    }

    private static void printArticleTable(List<BaseArticle> articles) {
        System.out.println(ARTICLE_TABLE_LINE);
        System.out.printf(
                "%-15s %-15s %-25s %-12s %-10s %-15s %-15s %-12s %-20s %-15s %-12s %-15s%n",
                "Typ", "Artikelnummer", "Artikelname", "Preis", "Bestand", "Mindestbestand",
                "Set", "Jahr", "Zustand", "HoloType", "Sprache", "1st Edition"
        );
        System.out.println(ARTICLE_TABLE_LINE);

        for (BaseArticle article : articles) {
            if (article instanceof Article normalArticle) {
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
            } else if (article instanceof PokemonCard pokemonCard) {
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
            }
        }

        System.out.println(ARTICLE_TABLE_LINE);
    }

    private static void printPokemonCardTable(List<PokemonCard> pokemonCards) {
        System.out.println(POKEMON_TABLE_LINE);
        System.out.printf(
                "%-15s %-25s %-12s %-10s %-15s %-12s %-20s %-15s %-12s %-15s%n",
                "Setnummer", "Kartenname", "Preis", "Bestand", "Set", "Jahr",
                "Zustand", "HoloType", "Sprache", "1st Edition"
        );
        System.out.println(POKEMON_TABLE_LINE);

        for (PokemonCard pokemonCard : pokemonCards) {
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
        System.out.println(ARTICLE_TABLE_LINE);
        System.out.printf(
                "%-15s %-15s %-25s %-12s %-10s %-15s %-15s %-12s %-20s %-15s %-12s %-15s%n",
                "Typ", "Artikelnummer", "Artikelname", "Preis", "Bestand", "Mindestbestand",
                "Set", "Jahr", "Zustand", "HoloType", "Sprache", "1st Edition"
        );
        System.out.println(ARTICLE_TABLE_LINE);

        if (article instanceof Article normalArticle) {
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
        } else if (article instanceof PokemonCard pokemonCard) {
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
        }

        System.out.println(ARTICLE_TABLE_LINE);
    }

    private static void printMovementTable(List<StockMovement> movements) {
        System.out.println(MOVEMENT_TABLE_LINE);
        System.out.printf("%-20s %-15s %-25s %-15s %-10s%n",
                "Zeitstempel", "Artikelnummer", "Artikelname", "Bewegung", "Menge");
        System.out.println(MOVEMENT_TABLE_LINE);

        for (StockMovement movement : movements) {
            System.out.printf("%-20s %-15s %-25s %-15s %-10d%n",
                    formatTimestamp(movement.getTimestamp()),
                    movement.getArticleID(),
                    movement.getArticleName(),
                    movement.getMovementType(),
                    movement.getAmount());
        }

        System.out.println(MOVEMENT_TABLE_LINE);
    }

    private static String formatTimestamp(java.time.LocalDateTime timestamp) {
        java.time.format.DateTimeFormatter formatter =
                java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return timestamp.format(formatter);
    }

    private static void deleteArticle(Scanner scanner, Inventory inventory) {
        String articleID = InputHelper.readNonEmptyString(scanner, "Artikelnummer zum Löschen eingeben: ");
        boolean removed = inventory.removeArticle(articleID);

        if (removed) {
            System.out.println("Artikel wurde gelöscht.");
        } else {
            System.out.println("Kein Artikel mit dieser Nummer gefunden.");
        }
    }

    private static void saveArticles(Scanner scanner, Inventory inventory) {
        String fileName = InputHelper.readNonEmptyString(scanner, "Dateiname für Artikel eingeben (z. B. articles.txt): ");

        try {
            inventory.saveToFile(fileName);
            System.out.println("Artikel wurden erfolgreich gespeichert.");
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Artikel: " + e.getMessage());
        }
    }

    private static void loadArticles(Scanner scanner, Inventory inventory) {
        String fileName = InputHelper.readNonEmptyString(scanner, "Dateiname für Artikel eingeben (z. B. articles.txt): ");

        try {
            inventory.loadFromFile(fileName);
            System.out.println("Artikel wurden erfolgreich geladen.");
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Artikel: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler in den Artikeldaten: " + e.getMessage());
        }
    }

    private static void showAllMovements(Inventory inventory) {
        List<StockMovement> allMovements = inventory.getAllMovements();

        if (allMovements.isEmpty()) {
            System.out.println("Noch keine Buchungen vorhanden.");
        } else {
            System.out.println("Gesamte Buchungshistorie:");
            printMovementTable(allMovements);
        }
    }

    private static void showMovementsByArticle(Scanner scanner, Inventory inventory) {
        String articleID = InputHelper.readNonEmptyString(scanner, "Artikelnummer für Historie eingeben: ");
        List<StockMovement> articleMovements = inventory.getMovementsByArticleID(articleID);

        if (articleMovements.isEmpty()) {
            System.out.println("Keine Buchungen für diesen Artikel vorhanden.");
        } else {
            System.out.println("Buchungshistorie für Artikel " + articleID + ":");
            printMovementTable(articleMovements);
        }
    }

    private static void saveMovements(Scanner scanner, Inventory inventory) {
        String fileName = InputHelper.readNonEmptyString(scanner, "Dateiname für Historie eingeben (z. B. movements.txt): ");

        try {
            inventory.saveMovementsToFile(fileName);
            System.out.println("Buchungshistorie wurde erfolgreich gespeichert.");
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Historie: " + e.getMessage());
        }
    }

    private static void loadMovements(Scanner scanner, Inventory inventory) {
        String fileName = InputHelper.readNonEmptyString(scanner, "Dateiname für Historie eingeben (z. B. movements.txt): ");

        try {
            inventory.loadMovementsFromFile(fileName);
            System.out.println("Buchungshistorie wurde erfolgreich geladen.");
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Historie: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler in den Historiendaten: " + e.getMessage());
        }
    }

    private static void savePokemonCardsToDatabase(Inventory inventory, PokemonCardRepository repository) {
        List<PokemonCard> pokemonCards = inventory.getAllPokemonCards();

        if (pokemonCards.isEmpty()) {
            System.out.println("Keine PokemonCards im aktuellen Inventory vorhanden.");
            return;
        }

        int savedCount = 0;

        for (PokemonCard pokemonCard : pokemonCards) {
            repository.saveOrUpdate(pokemonCard);
            savedCount++;
        }

        System.out.println(savedCount + " PokemonCards wurden in der Datenbank gespeichert.");
    }

    private static void loadPokemonCardsFromDatabase(Inventory inventory, PokemonCardRepository repository) {
        List<PokemonCard> dbCards = repository.findAll();

        if (dbCards.isEmpty()) {
            System.out.println("Keine PokemonCards in der Datenbank vorhanden.");
            return;
        }

        int loadedCount = 0;
        int skippedCount = 0;

        for (PokemonCard pokemonCard : dbCards) {
            try {
                inventory.addArticle(pokemonCard);
                loadedCount++;
            } catch (IllegalArgumentException e) {
                skippedCount++;
            }
        }

        System.out.println(loadedCount + " PokemonCards wurden aus der Datenbank ins Inventory geladen.");
        if (skippedCount > 0) {
            System.out.println(skippedCount + " Karten wurden übersprungen, weil sie bereits im Inventory vorhanden waren.");
        }
    }

    private static void showPokemonCardsFromDatabase(PokemonCardRepository repository) {
        List<PokemonCard> dbCards = repository.findAll();

        if (dbCards.isEmpty()) {
            System.out.println("Keine PokemonCards in der Datenbank vorhanden.");
        } else {
            System.out.println("PokemonCards direkt aus der Datenbank:");
            printPokemonCardTable(dbCards);
        }
    }

    private static void clearPokemonCardsDatabase(Scanner scanner, PokemonCardRepository repository) {
        boolean confirm = readYesNo(scanner, "Wirklich alle PokemonCards aus der DB löschen? (ja/nein): ");

        if (!confirm) {
            System.out.println("Löschen abgebrochen.");
            return;
        }

        repository.deleteAll();
        System.out.println("Alle PokemonCards wurden aus der Datenbank gelöscht.");
    }
}