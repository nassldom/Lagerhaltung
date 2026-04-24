import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Article> articles;
    private List<StockMovement> stockMovements;

    public Inventory() {
        this.articles = new ArrayList<>();
        this.stockMovements = new ArrayList<>();
    }

    public void addArticle(Article article) {
        if (article == null) {
            throw new IllegalArgumentException("Artikel darf nicht null sein.");
        }

        for (Article existingArticle : articles) {
            if (existingArticle.getArticleID().equalsIgnoreCase(article.getArticleID())) {
                throw new IllegalArgumentException("Artikel mit dieser Nummer existiert bereits.");
            }
        }

        articles.add(article);
    }

    public List<Article> getAllArticles() {
        return articles;
    }

    public Article getArticleByNumber(String articleID) {
        if (articleID == null || articleID.trim().isEmpty()) {
            throw new IllegalArgumentException("Artikelnummer darf nicht leer sein.");
        }

        for (Article article : articles) {
            if (article.getArticleID().equalsIgnoreCase(articleID.trim())) {
                return article;
            }
        }

        return null;
    }

    public Article getArticleByName(String articleName) {
        if (articleName == null || articleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Artikelname darf nicht leer sein.");
        }

        for (Article article : articles) {
            if (article.getArticleName().equalsIgnoreCase(articleName.trim())) {
                return article;
            }
        }

        return null;
    }

    public boolean removeArticle(String articleID) {
        Article article = getArticleByNumber(articleID);

        if (article == null) {
            return false;
        }

        return articles.remove(article);
    }

    public void increaseStock(String articleID, int amount) {
        Article article = getArticleByNumber(articleID);

        if (article == null) {
            throw new IllegalArgumentException("Artikel nicht gefunden.");
        }

        article.increaseStock(amount);
        stockMovements.add(new StockMovement(
                article.getArticleID(),
                article.getArticleName(),
                MovementType.IN,
                amount
        ));
    }

    public void decreaseStock(String articleID, int amount) {
        Article article = getArticleByNumber(articleID);

        if (article == null) {
            throw new IllegalArgumentException("Artikel nicht gefunden.");
        }

        article.decreaseStock(amount);
        stockMovements.add(new StockMovement(
                article.getArticleID(),
                article.getArticleName(),
                MovementType.OUT,
                amount
        ));
    }

    public List<Article> getLowStockItems() {
        List<Article> lowStockItems = new ArrayList<>();

        for (Article article : articles) {
            if (article.getStock() <= article.getMinimumStock()) {
                lowStockItems.add(article);
            }
        }

        return lowStockItems;
    }

    public List<StockMovement> getAllMovements() {
        return stockMovements;
    }

    public List<StockMovement> getMovementsByArticleID(String articleID) {
        if (articleID == null || articleID.trim().isEmpty()) {
            throw new IllegalArgumentException("Artikelnummer darf nicht leer sein.");
        }

        List<StockMovement> matchingMovements = new ArrayList<>();

        for (StockMovement movement : stockMovements) {
            if (movement.getArticleID().equalsIgnoreCase(articleID.trim())) {
                matchingMovements.add(movement);
            }
        }

        return matchingMovements;
    }

    public int getArticleCount() {
        return articles.size();
    }

    public int getTotalStock() {
        int totalStock = 0;

        for (Article article : articles) {
            totalStock += article.getStock();
        }

        return totalStock;
    }

    public int getLowStockCount() {
        return getLowStockItems().size();
    }

    public void saveToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Article article : articles) {
                writer.write(
                        article.getArticleName() + ";" +
                        article.getArticleID() + ";" +
                        article.getArticlePrice() + ";" +
                        article.getStock() + ";" +
                        article.getMinimumStock()
                );
                writer.newLine();
            }
        }
    }

    public void loadFromFile(String fileName) throws IOException {
        List<Article> loadedArticles = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(";");

                if (parts.length != 5) {
                    throw new IllegalArgumentException("Ungültiges Artikelformat in Datei: " + line);
                }

                String articleName = parts[0].trim();
                String articleID = parts[1].trim();
                BigDecimal articlePrice = new BigDecimal(parts[2].trim());
                int stock = Integer.parseInt(parts[3].trim());
                int minimumStock = Integer.parseInt(parts[4].trim());

                loadedArticles.add(new Article(articleName, articleID, articlePrice, stock, minimumStock));
            }
        }

        this.articles = loadedArticles;
    }

    public void saveMovementsToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (StockMovement movement : stockMovements) {
                writer.write(movement.toFileString());
                writer.newLine();
            }
        }
    }

    public void loadMovementsFromFile(String fileName) throws IOException {
        List<StockMovement> loadedMovements = new ArrayList<>();
        DateTimeFormatter formatter = StockMovement.getFileFormatter();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(";");

                if (parts.length != 5) {
                    throw new IllegalArgumentException("Ungültiges Bewegungsformat in Datei: " + line);
                }

                try {
                    LocalDateTime timestamp = LocalDateTime.parse(parts[0].trim(), formatter);
                    String articleID = parts[1].trim();
                    String articleName = parts[2].trim();
                    MovementType movementType = MovementType.valueOf(parts[3].trim());
                    int amount = Integer.parseInt(parts[4].trim());

                    loadedMovements.add(new StockMovement(
                            timestamp,
                            articleID,
                            articleName,
                            movementType,
                            amount
                    ));
                } catch (DateTimeParseException | IllegalArgumentException e) {
                    throw new IllegalArgumentException("Fehlerhafte Bewegungsdaten in Datei: " + line);
                }
            }
        }

        this.stockMovements = loadedMovements;
    }
}