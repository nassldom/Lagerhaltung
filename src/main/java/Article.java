/*
Diese Klasse beschreibt einen Artikel mit einem zusätzlichen Feld für den
Mindestlagerbestand. Wenn der aktuelle Bestand kleiner oder gleich diesem
Mindestwert ist, meldet hasLowStock() einen niedrigen Bestand. toString()
sorgt dafür, dass das Objekt lesbar ausgegeben werden kann.
*/
import java.math.BigDecimal;
// Importiert die Klasse BigDecimal aus java.math.
// Die Klasse braucht man hier für den Preis des Artikels.

public class Article extends BaseArticle {
// Definiert eine Klasse namens Article.
// extends BaseArticle bedeutet: Article erbt von BaseArticle.
// Article bekommt dadurch die Eigenschaften und Methoden der Basisklasse.

    private int minimumStock;
    // Privates Attribut für den Mindestlagerbestand.
    // private bedeutet: Nur diese Klasse kann direkt darauf zugreifen.

    public Article(String articleName, String articleID, BigDecimal articlePrice, int stock, int minimumStock) {
        // Konstruktor der Klasse Article.
        // Wird aufgerufen, wenn ein neues Article-Objekt erstellt wird.
        // Übergibt Name, ID, Preis, Bestand und Mindestbestand.
        super(articleName, articleID, articlePrice, stock);
        // Ruft den Konstruktor der Oberklasse BaseArticle auf.
        // Damit werden die gemeinsamen Felder dort initialisiert.

        if (minimumStock < 0) {  // Prüft, ob der Mindestlagerbestand negativ ist.
            throw new IllegalArgumentException("Mindestlagerbestand darf nicht negativ sein.");
        }
        // Wenn minimumStock kleiner als 0 ist, wird eine Ausnahme ausgelöst.
        // Dadurch werden ungültige Werte verhindert.
        this.minimumStock = minimumStock;
        // Speichert den übergebenen Wert im Attribut minimumStock.
        // this.minimumStock meint das Feld des Objekts.
    }

    public int getMinimumStock() {
        // Getter-Methode für minimumStock.
        // Gibt den aktuellen Mindestlagerbestand zurück.
        return minimumStock;
        // Liefert den Wert des Attributs zurück.
    }

    @Override
    public boolean hasLowStock() {
        // Überschreibt die Methode hasLowStock() aus der Oberklasse.
        // Prüft, ob der Bestand zu niedrig ist.

        return getStock() <= minimumStock;
        // Gibt true zurück, wenn der aktuelle Bestand kleiner oder gleich dem Mindestbestand ist.
    }

    @Override
    public String getArticleType() {
        // Überschreibt die Methode getArticleType().
        // Gibt den Typ des Artikels zurück.
        return "Artikel";
        // Startet die Textdarstellung des Objekts.
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleName='" + getArticleName() + '\'' + // Fügt den Artikelnamen ein.
                ", articleID='" + getArticleID() + '\'' +   // Fügt die Artikel-ID ein.
                ", articlePrice=" + getArticlePrice() +     // Fügt den Preis ein.
                ", stock=" + getStock() +                   // Fügt den aktuellen Bestand ein.
                ", minimumStock=" + minimumStock +          // Fügt den Mindestbestand ein.
                '}';                                        // Schließt die Textdarstellung ab.
    }
}