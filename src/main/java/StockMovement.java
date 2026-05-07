// Bewegungsdaten für Artikel. Jede Bewegung hat einen Zeitstempel, die Artikelnummer, den Artikelnamen, den
// Bewegungstyp (z.B. "Eingang", "Ausgang") und die Menge. Validiert die Eingaben im Konstruktor und bietet
// Getter-Methoden für alle Attribute sowie eine toString-Methode für die Ausgabe der Bewegungsdaten.
/*
StockMovement beschreibt eine einzelne Lagerbewegung mit Zeitstempel, Artikelnummer, Artikelname, Bewegungstyp und
Menge. Der Konstruktor prüft alle Eingaben, toFileString() erzeugt ein speicherbares Dateiformat, und toString()
liefert eine gut lesbare Ausgabe für Konsole oder Debugging.

Wichtige Punkte:
Der erste Konstruktor setzt den Zeitstempel automatisch auf LocalDateTime.now().
Der zweite Konstruktor erlaubt einen frei übergebenen Zeitstempel, z. B. beim Laden aus einer Datei.
FILE_FORMATTER ist static final, also eine gemeinsame Konstante für alle Objekte, was für Formatter ein üblicher und
sinnvoller Aufbau ist.
 */
import java.time.LocalDateTime;
// Importiert LocalDateTime.
// Diese Klasse speichert Datum und Uhrzeit ohne Zeitzone.
import java.time.format.DateTimeFormatter;
// Importiert DateTimeFormatter.
// Damit werden Datums- und Zeitwerte in Strings formatiert.

public class StockMovement {
    // Definiert die Klasse StockMovement.
    // Diese Klasse beschreibt eine einzelne Lagerbewegung.
    private static final DateTimeFormatter FILE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // Statische, unveränderliche Konstante für das Dateiformat in Dateien.
    // static final bedeutet: Es gibt genau eine gemeinsame Konstante für alle Objekte.
    // Das Muster ist z. B. 2026-05-07 17:07:00.
    private LocalDateTime timestamp;    // Speichert den Zeitstempel der Bewegung.
    private String articleID;           // Speichert die Artikelnummer des betroffenen Artikels.
    private String articleName;         // Speichert den Namen des Artikels.
    private MovementType movementType;  // Speichert die Art der Bewegung, z. B. IN oder OUT.
    private int amount;                 // Speichert die Menge der Bewegung.

    public StockMovement(String articleID, String articleName, MovementType movementType, int amount) {
        // Konstruktor ohne expliziten Zeitstempel.
        // Diese Variante setzt automatisch den aktuellen Zeitpunkt.
        this(LocalDateTime.now(), articleID, articleName, movementType, amount);
        // Ruft den zweiten Konstruktor auf und übergibt LocalDateTime.now()
        // als aktuellen Zeitstempel.
        // Das nennt man Konstruktorverkettung.
    }



    public StockMovement(LocalDateTime timestamp, String articleID, String articleName, MovementType movementType, int amount) {
        // Vollständiger Konstruktor mit allen Werten inklusive Zeitstempel.
        if (timestamp == null) {    // Prüft, ob der Zeitstempel null ist.
            throw new IllegalArgumentException("Zeitstempel darf nicht null sein.");
        }   // Wenn ja, wird eine Exception geworfen.

        if (articleID == null || articleID.trim().isEmpty()) {  // Prüft, ob die Artikelnummer null oder leer ist.
            throw new IllegalArgumentException("Artikelnummer darf nicht leer sein.");
        }     // Leere Artikelnummern sind nicht erlaubt.

        if (articleName == null || articleName.trim().isEmpty()) {  // Prüft, ob der Artikelname null oder leer ist.
            throw new IllegalArgumentException("Artikelname darf nicht leer sein.");
        }   // Leere Namen sind nicht erlaubt.

        if (movementType == null) {     // Prüft, ob kein Bewegungstyp übergeben wurde.
            throw new IllegalArgumentException("Bewegungstyp darf nicht null sein.");
        }   // Eine Bewegung muss einen Typ haben.

        if (amount <= 0) {  // Prüft, ob die Menge kleiner oder gleich 0 ist.
            throw new IllegalArgumentException("Menge muss größer als 0 sein.");
        }   // Nur positive Mengen sind erlaubt.

        this.timestamp = timestamp;     // Speichert den Zeitstempel im Attribut.
        this.articleID = articleID;     // Speichert die Artikelnummer.
        this.articleName = articleName; // Speichert den Artikelnamen.
        this.movementType = movementType;   // Speichert den Bewegungstyp.
        this.amount = amount;               // Speichert die Menge.
    }

    public LocalDateTime getTimestamp() {
        // Getter für den Zeitstempel.
        return timestamp;   // Gibt den Zeitstempel zurück
    }

    public String getArticleID() {
        // Getter für die Artikelnummer.
        return articleID;   // Gibt die Artikelnummer zurück.
    }

    public String getArticleName() {
        // Getter für den Artikelnamen.
        return articleName; // Gibt den Artikelnamen zurück.
    }

    public MovementType getMovementType() {
        // Getter für den Bewegungstyp.
        return movementType;    // Gibt den MovementType zurück.
    }

    public int getAmount() {
        // Getter für die Menge.
        return amount;  // Gibt die Menge zurück.
    }
    public String toFileString() {
        // Formatiert das Objekt als String für die Dateispeicherung.
        return timestamp.format(FILE_FORMATTER) + ";" +
                // Formatiert den Zeitstempel mit dem Dateiformat
                // und fügt ein Semikolon an.
               articleID + ";" +                            // Fügt die Artikelnummer an.
               articleName + ";" +                          // Fügt den Artikelnamen an.
                movementType.name() + ";" +                 // Fügt den Enum-Namen des Bewegungstyps an, z. B. IN oder OUT.
               amount;
        // Fügt die Menge an.
        // Das Ergebnis ist eine Zeile im Format:
        // Zeitstempel; Artikelnummer; Artikelname; Bewegungstyp;Menge
    }

    public static DateTimeFormatter getFileFormatter() {
        // Statische Methode, um den FILE_FORMATTER von außen verfügbar zu machen.
        return FILE_FORMATTER;  // Gibt den Formatter zurück.
    }
    @Override
    public String toString() {
        // Überschreibt die toString()-Methode.
        // Damit kann das Objekt sinnvoll als Text ausgegeben werden.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        // Erstellt einen Formatter für eine lesbare Anzeige,
        // z. B. 07.05.2026 17:07:00.

        return "StockMovement{" +                               // Beginnt die Textdarstellung des Objekts.
                "timestamp=" + timestamp.format(formatter) +    // Fügt den formatierten Zeitstempel ein.
                ", articleID='" + articleID + '\'' +            // Fügt die Artikelnummer ein.
                ", articleName='" + articleName + '\'' +        // Fügt den Artikelnamen ein.
                ", movementType='" + movementType + '\'' +      // Fügt den Bewegungstyp ein.
                ", amount=" + amount +                          // Fügt die Menge ein.
                '}';                                            // Schließt die String-Darstellung ab.
    }
}