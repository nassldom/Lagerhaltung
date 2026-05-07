/*
PokemonCard ist eine Unterklasse von BaseArticle und erweitert die allgemeinen Artikeldaten um Pokémon spezifische
Informationen wie Set, Jahr, Zustand, HoloType, Sprache und First Edition. Durch final sind diese zusätzlichen
Eigenschaften nach der Erstellung unveränderlich, was gut zu festen Stammdaten eines Objekts passt.
 */

import java.math.BigDecimal;
// Importiert die Klasse BigDecimal.
// Sie wird für den Preis der Karte verwendet.

public class PokemonCard extends BaseArticle {
    // Definiert die Klasse PokemonCard.
    // extends BaseArticle bedeutet: PokemonCard erbt von BaseArticle
    // und übernimmt damit gemeinsame Eigenschaften wie Name, ID, Preis und Bestand.

    private final String setName;
    // Unveränderliches Attribut für den Namen des Sets.
    // final bedeutet: Nach dem Setzen im Konstruktor kann es nicht mehr geändert werden.
    private final int releaseYear;
    // Unveränderliches Attribut für das Erscheinungsjahr.
    private final CardCondition condition;
    // Unveränderliches Attribut für den Zustand der Karte.
    // Verwendet die Enum CardCondition.
    private final HoloType holoType;
    // Unveränderliches Attribut für den Holo-Typ der Karte.
    // Verwendet die Enum HoloType.
    private final String language;
    // Unveränderliches Attribut für die Sprache der Karte.
    private final boolean firstEdition;
    // Unveränderliches Attribut dafür, ob es sich um eine FirstEdition handelt.
    // boolean kann nur true oder false sein.

    public PokemonCard(String articleName,
                       String articleID,
                       BigDecimal articlePrice,
                       int stock,
                       String setName,
                       int releaseYear,
                       CardCondition condition,
                       HoloType holoType,
                       String language,
                       boolean firstEdition) {
        // Konstruktor der Klasse PokemonCard.
        // Er erhält alle Werte, die zum Erzeugen einer Karte benötigt werden.
        super(articleName, articleID, articlePrice, stock);
        // Ruft zuerst den Konstruktor der Oberklasse BaseArticle auf.
        // Dort werden die gemeinsamen Basisdaten geprüft und gespeichert.

        if (setName == null || setName.trim().isEmpty()) {
            // Prüft, ob der Setname null oder leer ist.
            throw new IllegalArgumentException("Setname darf nicht leer sein.");
            // Wenn der Setname ungültig ist, wird eine Exception ausgelöst.
            // Solche Validierungen direkt im Konstruktor sind ein üblicher Weg,
            // um nur gültige Objekte zuzulassen.
        }

        if (releaseYear < 1900) {    // Prüft, ob das Jahr kleiner als 1900 ist.
            throw new IllegalArgumentException("Jahr ist ungültig.");   // Ein unrealistisches Jahr wird abgelehnt.
        }

        if (condition == null) {    // Prüft, ob kein Zustand übergeben wurde.
            throw new IllegalArgumentException("Zustand darf nicht null sein.");
            // Eine Karte muss einen Zustand haben.
        }

        if (holoType == null) {     // Prüft, ob kein HoloType übergeben wurde.
            throw new IllegalArgumentException("HoloType darf nicht null sein.");   // Auch der HoloType ist Pflicht.
        }

        if (language == null || language.trim().isEmpty()) {    // Prüft, ob die Sprache null oder leer ist.
            throw new IllegalArgumentException("Sprache darf nicht leer sein.");    // Leere Sprache ist nicht erlaubt.
        }

        this.setName = setName;             // Speichert den Setnamen im Attribut.
        this.releaseYear = releaseYear;     // Speichert das Erscheinungsjahr.
        this.condition = condition;         // Speichert den Zustand.
        this.holoType = holoType;           // Speichert den HoloType.
        this.language = language.trim();    // Speichert die Sprache, aber vorher werden Leerzeichen am Anfang und Ende entfernt.
        this.firstEdition = firstEdition;   // Speichert, ob die Karte eine FirstEdition ist.
    }

    public String getSetName() {    // Getter für den Setnamen.
        return setName;             // Gibt den Setnamen zurück.
    }

    public int getReleaseYear() {   // Getter für das Erscheinungsjahr.
        return releaseYear;         // Gibt das Jahr zurück.
    }

    public CardCondition getCondition() {   // Getter für den Kartenzustand.
        return condition;                   // Gibt den Zustand als CardCondition zurück.
    }

    public HoloType getHoloType() {         // Getter für den HoloType.
        return holoType;                    // Gibt den HoloType zurück.
    }

    public String getLanguage() {           // Getter für die Sprache.
        return language;                    // Gibt die Sprache zurück.
    }

    public boolean isFirstEdition() {
        // Getter für das boolean-Feld firstEdition.
        // Bei boolean verwendet man häufig is... statt get...
        return firstEdition;                // Gibt true oder false zurück.

    }

    @Override
    public boolean hasLowStock() {  // Überschreibt die abstrakte Methode aus BaseArticle.
        return false;
        // Gibt immer false zurück.
        // Das bedeutet: Für PokemonCard ist aktuell keine Low-Stock-Logik definiert.
    }

    @Override
    public String getArticleType() {
        // Überschreibt die abstrakte Methode aus BaseArticle.
        return "PokemonCard";   // Gibt den Typ dieses Artikels als String zurück.
    }

    @Override
    public String toString() {
        // Überschreibt toString() für eine sinnvolle Textdarstellung des Objekts.
        // Das ist nützlich für Debugging und Ausgaben.
        return "PokemonCard{" + // Start der String-Darstellung.
                "articleName='" + getArticleName() + '\'' + // Fügt den Artikelnamen ein.
                ", articleID='" + getArticleID() + '\'' +   // Fügt die Artikel-ID ein.
                ", articlePrice=" + getArticlePrice() +     // Fügt den Preis ein.
                ", stock=" + getStock() +                   // Fügt den Bestand ein.
                ", setName='" + setName + '\'' +            // Fügt den Set namen ein.
                ", releaseYear=" + releaseYear +            // Fügt das Erscheinungsjahr ein.
                ", condition=" + condition +                // Fügt den Zustand ein.
                ", holoType=" + holoType +                  // Fügt den HoloType ein.
                ", language='" + language + '\'' +          // Fügt die Sprache ein.
                ", firstEdition=" + firstEdition +          // Fügt den First Edition Status ein.
                '}';                                        // Schließt die Textdarstellung ab.
    }
}