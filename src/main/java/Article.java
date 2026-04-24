// Beschreibt einen Artikel mit Name, ID, Preis, Lagerbestand und Mindestlagerbestand. Validiert die Eingaben im Konstruktor 
// und bietet Getter-Methoden für alle Attribute sowie eine Methode zum Aktualisieren des Lagerbestands.

import java.math.BigDecimal;

public class Article {
    private String articleName;     // Private bedeutet: Von außen darf man nicht direkt auf diese Felder zugreifen -> Kapselung
    private String articleID;
    private BigDecimal articlePrice;
    private int stock;
    private int minimumStock;

// Konstruktor: "Wenn ein neues Article-Object gebaut wird, dann bekommt es genau diese Startwerte."

    public Article(String articleName, String articleID, BigDecimal articlePrice, int stock, int minimumStock) {
        if (articleName == null || articleName.trim().isEmpty()) {     // trim() entfernt führende und nachgestellte Leerzeichen, damit als leer behandelt                                             
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

        if (minimumStock < 0) {
            throw new IllegalArgumentException("Mindestlagerbestand darf nicht negativ sein.");
        }

// "Speichere den übergebenen Namen im neuen Objekt ab"

        this.articleName = articleName;
        this.articleID = articleID;
        this.articlePrice = articlePrice;
        this.stock = stock;
        this.minimumStock = minimumStock;
    }

// Getter-Methoden: "Wenn jemand den Namen eines Artikels wissen will, dann soll er diese Methode aufrufen und sie gibt den Namen zurück."
// Setter-Methode: "Wenn jemand den Lagerbestand eines Artikels ändern will, dann soll er diese Methode aufrufen und den neuen Lagerbestand übergeben."

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

    public int getMinimumStock() {
        return minimumStock;
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

    @Override
    public String toString() {
        return "Article{" +
                "articleName='" + articleName + '\'' +
                ", articleID='" + articleID + '\'' +
                ", articlePrice=" + articlePrice +
                ", stock=" + stock +
                ", minimumStock=" + minimumStock +
                '}';
    }
}