// Bewegungsdaten für Artikel. Jede Bewegung hat einen Zeitstempel, die Artikelnummer, den Artikelnamen, den Bewegungstyp (z.B. "Eingang", "Ausgang") und die Menge. Validiert die Eingaben im Konstruktor und bietet Getter-Methoden für alle Attribute sowie eine toString-Methode für die Ausgabe der Bewegungsdaten.

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StockMovement {
    private static final DateTimeFormatter FILE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime timestamp;
    private String articleID;
    private String articleName;
    private MovementType movementType;
    private int amount;

    public StockMovement(String articleID, String articleName, MovementType movementType, int amount) {
        this(LocalDateTime.now(), articleID, articleName, movementType, amount);
    }

    
    public StockMovement(LocalDateTime timestamp, String articleID, String articleName, MovementType movementType, int amount) {
        if (timestamp == null) {
            throw new IllegalArgumentException("Zeitstempel darf nicht null sein."); 
        }
       
        if (articleID == null || articleID.trim().isEmpty()) {
            throw new IllegalArgumentException("Artikelnummer darf nicht leer sein.");
        }

        if (articleName == null || articleName.trim().isEmpty()) {
            throw new IllegalArgumentException("Artikelname darf nicht leer sein.");
        }

        if (movementType == null) {
            throw new IllegalArgumentException("Bewegungstyp darf nicht null sein.");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Menge muss größer als 0 sein.");
        }

        this.timestamp = timestamp;
        this.articleID = articleID;
        this.articleName = articleName;
        this.movementType = movementType;
        this.amount = amount; 
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getArticleID() {
        return articleID;
    }

    public String getArticleName() {
        return articleName;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public int getAmount() {
        return amount;
    }
    public String toFileString() {
        return timestamp.format(FILE_FORMATTER) + ";" +
               articleID + ";" +
               articleName + ";" +
               movementType.name() + ";" +
               amount;
    }

    public static DateTimeFormatter getFileFormatter() {
        return FILE_FORMATTER;
    }    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        return "StockMovement{" +
                "timestamp=" + timestamp.format(formatter) +
                ", articleID='" + articleID + '\'' +
                ", articleName='" + articleName + '\'' +
                ", movementType='" + movementType + '\'' +
                ", amount=" + amount +
                '}';
    }
}