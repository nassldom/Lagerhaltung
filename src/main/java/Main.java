import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Inventory inventory = new Inventory();

        int option = -1;

        while (option != 0) {
            printMenu();

            if (!scanner.hasNextInt()) {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                scanner.nextLine();
                continue;
            }

            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    createArticle(scanner, inventory);
                    break;

                case 2:
                    showAllArticles(inventory);
                    break;

                case 3:
                    searchArticleByID(scanner, inventory);
                    break;

                case 4:
                    searchArticleByName(scanner, inventory);
                    break;

                case 5:
                    bookIncomingStock(scanner, inventory);
                    break;

                case 6:
                    bookOutgoingStock(scanner, inventory);
                    break;

                case 7:
                    showLowStockItems(inventory);
                    break;

                case 8:
                    deleteArticle(scanner, inventory);
                    break;

                case 9:
                    saveArticles(scanner, inventory);
                    break;

                case 10:
                    loadArticles(scanner, inventory);
                    break;

                case 11:
                    showAllMovements(inventory);
                    break;

                case 12:
                    showMovementsByArticle(scanner, inventory);
                    break;

                case 13:
                    saveMovements(scanner, inventory);
                    break;

                case 14:
                    loadMovements(scanner, inventory);
                    break;

                case 15:
                    showStatistics(inventory);
                    break;

                case 0:
                    System.out.println("Programm beendet.");
                    break;

                default:
                    System.out.println("Ungültige Option. Bitte erneut wählen.");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== Lagerverwaltung ===");
        System.out.println();
        System.out.println("1  - Artikel anlegen");
        System.out.println("2  - Alle Artikel anzeigen");
        System.out.println("3  - Artikel nach Nummer suchen");
        System.out.println("4  - Artikel nach Name suchen");
        System.out.println("5  - Zugang buchen");
        System.out.println("6  - Abgang buchen");
        System.out.println("7  - Kritische Bestände anzeigen");
        System.out.println("8  - Artikel löschen");
        System.out.println("9  - Artikel in Datei speichern");
        System.out.println("10 - Artikel aus Datei laden");
        System.out.println("11 - Gesamte Buchungshistorie anzeigen");
        System.out.println("12 - Buchungshistorie eines Artikels anzeigen");
        System.out.println("13 - Buchungshistorie in Datei speichern");
        System.out.println("14 - Buchungshistorie aus Datei laden");
        System.out.println("15 - Lagerstatistik anzeigen");
        System.out.println("0  - Beenden");
        System.out.println();
        System.out.print("Bitte Option wählen: ");
        System.out.println();
    }

    private static void printArticleTable(List<Article> articles) {
        System.out.println(
                "--------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-25s %-12s %-12s %-15s%n",
                "Artikelnummer", "Artikelname", "Preis", "Bestand", "Mindestbestand");
        System.out.println(
                "--------------------------------------------------------------------------------------------------");

        for (Article article : articles) {
            System.out.printf("%-15s %-25s %-12s %-12d %-15d%n",
                    article.getArticleID(),
                    article.getArticleName(),
                    article.getArticlePrice(),
                    article.getStock(),
                    article.getMinimumStock());
        }

        System.out.println(
                "--------------------------------------------------------------------------------------------------");
    }

    private static void showStatistics(Inventory inventory) {
        System.out.println("----- Lagerstatistik -----");
        System.out.println("Anzahl Artikel: " + inventory.getArticleCount());
        System.out.println("Gesamtbestand: " + inventory.getTotalStock());
        System.out.println("Kritische Artikel: " + inventory.getLowStockCount());
    }

    private static void createArticle(Scanner scanner, Inventory inventory) {
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

    private static void showAllArticles(Inventory inventory) {
        List<Article> allArticles = inventory.getAllArticles();

        if (allArticles.isEmpty()) {
            System.out.println("Keine Artikel vorhanden.");
        } else {
            System.out.println("Alle Artikel:");
            printArticleTable(allArticles);
        }
    }

    private static void searchArticleByID(Scanner scanner, Inventory inventory) {
        String searchID = InputHelper.readNonEmptyString(scanner, "Artikelnummer eingeben: ");
        Article article = inventory.getArticleByNumber(searchID);

        if (article != null) {
            System.out.println("Gefundener Artikel:");
            printSingleArticle(article);
        } else {
            System.out.println("Kein Artikel mit dieser Nummer gefunden.");
        }
    }

    private static void searchArticleByName(Scanner scanner, Inventory inventory) {
        String searchName = InputHelper.readNonEmptyString(scanner, "Artikelname eingeben: ");
        Article article = inventory.getArticleByName(searchName);

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
        List<Article> lowStockItems = inventory.getLowStockItems();

        if (lowStockItems.isEmpty()) {
            System.out.println("Keine kritischen Bestände vorhanden.");
        } else {
            System.out.println("Kritische Bestände:");
            printArticleTable(lowStockItems);
        }
    }

    private static void printSingleArticle(Article article) {
        System.out.println(
                "--------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-25s %-12s %-12s %-15s%n",
                "Artikelnummer", "Artikelname", "Preis", "Bestand", "Mindestbestand");
        System.out.println(
                "--------------------------------------------------------------------------------------------------");

        System.out.printf("%-15s %-25s %-12s %-12d %-15d%n",
                article.getArticleID(),
                article.getArticleName(),
                article.getArticlePrice(),
                article.getStock(),
                article.getMinimumStock());

        System.out.println(
                "--------------------------------------------------------------------------------------------------");
    }

    private static void printMovementTable(List<StockMovement> movements) {
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s %-15s %-25s %-15s %-10s%n",
                "Zeitstempel", "Artikelnummer", "Artikelname", "Bewegung", "Menge");
        System.out.println(
                "----------------------------------------------------------------------------------------------------------------");

        for (StockMovement movement : movements) {
            System.out.printf("%-20s %-15s %-25s %-15s %-10d%n",
                    formatTimestamp(movement.getTimestamp()),
                    movement.getArticleID(),
                    movement.getArticleName(),
                    movement.getMovementType(),
                    movement.getAmount());
        }

        System.out.println(
                "----------------------------------------------------------------------------------------------------------------");
    }

    private static String formatTimestamp(java.time.LocalDateTime timestamp) {
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter
                .ofPattern("dd.MM.yyyy HH:mm:ss");
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
        String fileName = InputHelper.readNonEmptyString(scanner,
                "Dateiname für Artikel eingeben (z. B. articles.txt): ");

        try {
            inventory.saveToFile(fileName);
            System.out.println("Artikel wurden erfolgreich gespeichert.");
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Artikel: " + e.getMessage());
        }
    }

    private static void loadArticles(Scanner scanner, Inventory inventory) {
        String fileName = InputHelper.readNonEmptyString(scanner,
                "Dateiname für Artikel eingeben (z. B. articles.txt): ");

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
        String fileName = InputHelper.readNonEmptyString(scanner,
                "Dateiname für Historie eingeben (z. B. movements.txt): ");

        try {
            inventory.saveMovementsToFile(fileName);
            System.out.println("Buchungshistorie wurde erfolgreich gespeichert.");
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Historie: " + e.getMessage());
        }
    }

    private static void loadMovements(Scanner scanner, Inventory inventory) {
        String fileName = InputHelper.readNonEmptyString(scanner,
                "Dateiname für Historie eingeben (z. B. movements.txt): ");

        try {
            inventory.loadMovementsFromFile(fileName);
            System.out.println("Buchungshistorie wurde erfolgreich geladen.");
        } catch (IOException e) {
            System.out.println("Fehler beim Laden der Historie: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Fehler in den Historiendaten: " + e.getMessage());
        }
    }
}