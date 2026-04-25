import java.math.BigDecimal;

public abstract class BaseArticle {

    private String articleName;
    private String articleID;
    private BigDecimal articlePrice;
    private int stock;

    public BaseArticle(String articleName, String articleID, BigDecimal articlePrice, int stock) {
        if (articleName == null || articleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Artikelname darf nicht leer sein.");
        }

        if (articleID == null || articleID.trim().isEmpty()) {
            throw new IllegalArgumentException("Artikelnummer darf nicht leer sein.");
        }

        if (articlePrice == null || articlePrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Artikelpreis muss positiv sein.");
        }

        if (stock < 0) {
            throw new IllegalArgumentException("Lagerbestand darf nicht negativ sein.");
        }

        this.articleName = articleName;
        this.articleID = articleID;
        this.articlePrice = articlePrice;
        this.stock = stock;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getArticleID() {
        return articleID;
    }

    public BigDecimal getArticlePrice() {
        return articlePrice;
    }

    public int getStock() {
        return stock;
    }

    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Zugangsmenge muss größer als 0 sein.");
        }
        this.stock += amount;
    }

    public void decreaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Abgangsmenge muss größer als 0 sein.");
        }

        if (this.stock - amount < 0) {
            throw new IllegalArgumentException("Nicht genügend Bestand verfügbar.");
        }

        this.stock -= amount;
    }

    public abstract boolean hasLowStock();

    public abstract String getArticleType();
}