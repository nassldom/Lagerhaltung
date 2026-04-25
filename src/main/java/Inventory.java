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

    private List<BaseArticle> articles;
    private List<StockMovement> stockMovements;

    public Inventory() {
        this.articles = new ArrayList<>();
        this.stockMovements = new ArrayList<>();
    }

    public void addArticle(BaseArticle article) {
        if (article == null) {
            throw new IllegalArgumentException("Artikel darf nicht null sein.");
        }

        for (BaseArticle existingArticle : articles) {
            if (existingArticle.getArticleID().equalsIgnoreCase(article.getArticleID())) {
                throw new IllegalArgumentException("Artikel mit dieser Nummer existiert bereits.");
            }
        }

        articles.add(article);
    }

    public List<BaseArticle> getAllArticles() {
        return articles;
    }

    public List<PokemonCard> getAllPokemonCards() {
        List<PokemonCard> pokemonCards = new ArrayList<>();

        for (BaseArticle article : articles) {
            if (article instanceof PokemonCard pokemonCard) {
                pokemonCards.add(pokemonCard);
            }
        }

        return pokemonCards;
    }

    public BaseArticle getArticleByNumber(String articleID) {
        if (articleID == null || articleID.trim().isEmpty()) {
            throw new IllegalArgumentException("Artikelnummer darf nicht leer sein.");
        }

        for (BaseArticle article : articles) {
            if (article.getArticleID().equalsIgnoreCase(articleID.trim())) {
                return article;
            }
        }

        return null;
    }

    public BaseArticle getArticleByName(String articleName) {
        if (articleName == null || articleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Artikelname darf nicht leer sein.");
        }

        for (BaseArticle article : articles) {
            if (article.getArticleName().equalsIgnoreCase(articleName.trim())) {
                return article;
            }
        }

        return null;
    }

    public boolean removeArticle(String articleID) {
        BaseArticle article = getArticleByNumber(articleID);

        if (article == null) {
            return false;
        }

        return articles.remove(article);
    }

    public void increaseStock(String articleID, int amount) {
        BaseArticle article = getArticleByNumber(articleID);

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
        BaseArticle article = getArticleByNumber(articleID);

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

    public List<BaseArticle> getLowStockItems() {
        List<BaseArticle> lowStockItems = new ArrayList<>();

        for (BaseArticle article : articles) {
            if (article.hasLowStock()) {
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

        for (BaseArticle article : articles) {
            totalStock += article.getStock();
        }

        return totalStock;
    }

    public int getLowStockCount() {
        return getLowStockItems().size();
    }

    public void saveToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (BaseArticle article : articles) {
                if (article instanceof Article normalArticle) {
                    writer.write(
                            "ARTICLE;" +
                                    normalArticle.getArticleName() + ";" +
                                    normalArticle.getArticleID() + ";" +
                                    normalArticle.getArticlePrice() + ";" +
                                    normalArticle.getStock() + ";" +
                                    normalArticle.getMinimumStock()
                    );
                } else if (article instanceof PokemonCard pokemonCard) {
                    writer.write(
                            "POKEMONCARD;" +
                                    pokemonCard.getArticleName() + ";" +
                                    pokemonCard.getArticleID() + ";" +
                                    pokemonCard.getArticlePrice() + ";" +
                                    pokemonCard.getStock() + ";" +
                                    pokemonCard.getSetName() + ";" +
                                    pokemonCard.getReleaseYear() + ";" +
                                    pokemonCard.getCondition().name() + ";" +
                                    pokemonCard.getHoloType().name() + ";" +
                                    pokemonCard.getLanguage() + ";" +
                                    pokemonCard.isFirstEdition()
                    );
                }
                writer.newLine();
            }
        }
    }

    public void loadFromFile(String fileName) throws IOException {
        List<BaseArticle> loadedArticles = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(";");

                if (parts[0].equals("ARTICLE")) {
                    if (parts.length != 6) {
                        throw new IllegalArgumentException("Ungültiges Artikelformat in Datei: " + line);
                    }

                    String articleName = parts[1].trim();
                    String articleID = parts[2].trim();
                    BigDecimal articlePrice = new BigDecimal(parts[3].trim());
                    int stock = Integer.parseInt(parts[4].trim());
                    int minimumStock = Integer.parseInt(parts[5].trim());

                    loadedArticles.add(new Article(articleName, articleID, articlePrice, stock, minimumStock));
                } else if (parts[0].equals("POKEMONCARD")) {
                    if (parts.length != 11) {
                        throw new IllegalArgumentException("Ungültiges PokemonCard-Format in Datei: " + line);
                    }

                    String articleName = parts[1].trim();
                    String articleID = parts[2].trim();
                    BigDecimal articlePrice = new BigDecimal(parts[3].trim());
                    int stock = Integer.parseInt(parts[4].trim());
                    String setName = parts[5].trim();
                    int releaseYear = Integer.parseInt(parts[6].trim());
                    CardCondition condition = CardCondition.valueOf(parts[7].trim());
                    HoloType holoType = HoloType.valueOf(parts[8].trim());
                    String language = parts[9].trim();
                    boolean firstEdition = Boolean.parseBoolean(parts[10].trim());

                    loadedArticles.add(new PokemonCard(
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
                    throw new IllegalArgumentException("Unbekannter Artikeltyp in Datei: " + line);
                }
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