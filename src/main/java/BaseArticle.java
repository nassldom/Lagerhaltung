/*
BaseArticle ist die gemeinsame Oberklasse für alle Arten von Artikeln. Sie enthält Name, ID, Preis und Bestand,
komplett mit Read‑only‑Gettern und Methoden zum Erhöhen/Verkleinern des Bestands. Zwei Methoden sind abstrakt,
also müssen sie in Unterklassen wie Article implementiert werden.
*/
import java.math.BigDecimal;
// Importiert die Klasse BigDecimal aus java.math.
// Sie wird verwendet, um den Artikelpreis exakt als Dezimalzahl zu speichern (z. B. für Geldbeträge).

public abstract class BaseArticle {
    // Definiert eine abstrakte Basisklasse namens BaseArticle.
    // abstract: Man kann keine Instanz dieser Klasse direkt erstellen.
    // Sie soll als Oberklasse für andere Artikel‑Klassen (z. B. Article) dienen.

    private String articleName;
    // Privates Attribut für den Artikelnamen.
    // private: Nur innerhalb dieser Klasse zugreifbar.
    private String articleID;
    // Privates Attribut für die Artikelnummer/ID.
    private BigDecimal articlePrice;
    // Privates Attribut für den Artikelpreis.
    // BigDecimal wird genutzt, um Rundungsfehler bei Fließkommazahlen zu vermeiden.

    private int stock;
    // Privates Attribut für den aktuellen Lagerbestand (Anzahl der Artikel).

    public BaseArticle(String articleName, String articleID, BigDecimal articlePrice, int stock) {
        // Konstruktor der Basisklasse.
        // Wird beim Erzeugen konkreter Artikel (z. B. von Article) aufgerufen.
        if (articleName == null || articleName.trim().isEmpty()) {
            // Prüft, ob articleName null ist
            // oder nach dem Entfernen von Leerzeichen ("trim") leer ist.
            throw new IllegalArgumentException("Artikelname darf nicht leer sein.");
            // Wenn die Bedingung zutrifft, wird eine Ausnahme geworfen.
            // So wird sichergestellt, dass kein leerer Name zugelassen wird.
        }

        if (articleID == null || articleID.trim().isEmpty()) {
            throw new IllegalArgumentException("Artikelnummer darf nicht leer sein.");
            // Wenn ja, wird eine Ausnahme ausgelöst.
        }

        if (articlePrice == null || articlePrice.compareTo(BigDecimal.ZERO) < 0) {
            // Prüft, ob articlePrice null ist
            // oder kleiner als 0 (negativ) ist (über BigDecimal.compareTo()).
            throw new IllegalArgumentException("Artikelpreis muss positiv sein.");
        }

        if (stock < 0) {
            // Prüft, ob der Lagerbestand negativ ist.
            throw new IllegalArgumentException("Lagerbestand darf nicht negativ sein.");
            // Negativer Bestand ist unzulässig.
        }

        this.articleName = articleName;
        // Speichert den übergebenen Artikelnamen im Attribut.
        this.articleID = articleID;
        // Speichert die Artikelnummer.
        this.articlePrice = articlePrice;
        // Speichert den Preis.
        this.stock = stock;
        // Speichert den Bestand.
    }

    public String getArticleName() {
        // Getter‑Methode für articleName.
        // Gibt den aktuellen Artikelnamen zurück.
        return articleName;
        // Liefert den Namen zurück.
    }

    public String getArticleID() {
        // Getter für articleID.
        return articleID;
    }

    public BigDecimal getArticlePrice() {
        // Getter für articlePrice.
        return articlePrice;
    }

    public int getStock() {
        // Getter für den Bestand (stock).
        return stock;
    }

    public void increaseStock(int amount) {
        // Methode, um den Lagerbestand zu erhöhen.
        // amount: die Anzahl der hinzuzufügenden Artikel.
        if (amount <= 0) {
            // Prüft, ob die Zugangsmenge nicht positiv ist.
            throw new IllegalArgumentException("Zugangsmenge muss größer als 0 sein.");
            // Ungültige Eingabe wird mit einer Ausnahme abgelehnt.
        }
        this.stock += amount;
        // Erhöht den Bestand um amount.
    }

    public void decreaseStock(int amount) {
        // Methode, um den Bestand zu verringern.
        if (amount <= 0) {
            // Prüft, ob die Abgangsmenge nicht positiv ist.
            throw new IllegalArgumentException("Abgangsmenge muss größer als 0 sein.");
            // Ungültige Eingabe wird abgelehnt.
        }

        if (this.stock - amount < 0) {
            // Prüft, ob nach dem Abzug Bestand negativ wäre.
            throw new IllegalArgumentException("Nicht genügend Bestand verfügbar.");
            // Es darf nicht mehr weggenommen werden, als vorhanden ist.

    }

        this.stock -= amount;
        // Verringert den Bestand um amount.
    }

    public abstract boolean hasLowStock();
    // Abstrakte Methode, die prüfen soll, ob der Bestand „zu niedrig“ ist.
    // abstract: Die Methode hat keinen Methodenkörper hier.
    // Konkrete Unterklassen (z. B. Article) müssen sie implementieren.

    public abstract String getArticleType();
    // Abstrakte Methode, die den Typ des Artikels zurückgibt.
    // Wird z. B. in Article mit "Artikel" überschrieben.
}