/*
CardCondition ist eine Enum für Karten‑Zustände mit Lang‑ und Kurzform, sowie einer formatierten Darstellung.
Die Methode fromInput(String) erlaubt es, aus verschiedenen Text‑Eingaben (z.B. "Gem Mint", "GM", "Gem-Mint")
den passenden Enum‑Wert zu finden. toString() sorgt dafür, dass der Zustand in einem lesbaren Format ausgegeben wird.
*/
public enum CardCondition {
    // Definiert eine öffentliche Enumeration (Enum) namens CardCondition.
    // Eine Enum ist eine spezielle Klasse, die eine feste Menge von Konstanten (Zuständen) beschreibt.
    GEM_MINT("Gem Mint", "GM"),               // Konstanten: Upper Snake Case
    MINT("Mint", "M"),
    NEAR_MINT("Near Mint", "NM"),
    EXCELLENT("Excellent", "EX"),
    GOOD("Good", "GD"),
    LIGHT_PLAYED("Light Played", "LP"),
    PLAYED("Played", "PL"),
    POOR("Poor", "PO");

    private final String displayName;
    // Privates, unveränderliches Attribut für den langen Anzeigetext (z.B. "Gem Mint").
    private final String shortName;
    // Privates, unveränderliches Attribut für die Kurzform (z.B. "GM").

    CardCondition(String displayName, String shortName) {
        // Konstruktor der Enum‑Konstanten.
        // Wird einmalig pro Konstante beim Laden der Klasse aufgerufen.
        this.displayName = displayName;     // Speichert den übergebenen Anzeigetext im Attribut.
        this.shortName = shortName;         // Speichert die Kurzform im Attribut.
    }

    public String getDisplayName() {
        // Getter‑Methode für displayName.
        // Gibt den langen Namen des Zustands zurück.
        return displayName;
    }

    public String getShortName() {
        return shortName;
    }       // Getter für shortName

    public String getFormattedDisplay() {
        // Gibt eine formatierte Zeichenkette des Zustands zurück,
        // z.B. "Gem Mint (GM)".
        return displayName + " (" + shortName + ")";
        // Kombiniert Langname und Kurzname in einer lesbaren Form.
    }

    public static CardCondition fromInput(String input) {
        // Statische Hilfsmethode, um aus einer Zeichenkette einen CardCondition‑Wert zu ermitteln.
        if (input == null || input.trim().isEmpty()) {
            // Prüft auf null oder leere Eingabe (nach Trim).
            throw new IllegalArgumentException("Zustand darf nicht leer sein.");
            // Ungültige Eingabe wird mit einer Ausnahme abgelehnt.
        }

        String normalizedInput = input.trim()
                .replace("-", "_")
                .replace(" ", "_");
        // Bereinigt die Eingabe:
        // - entfernt Leerzeichen am Anfang/Ende
        // - ersetzt Minus durch Unterstrich
        // - ersetzt Leerzeichen durch Unterstrich
        // damit kann z.B. "Gem Mint" oder "Gem-Mint" zum Enum‑Namen GEM_MINT passen.

        for (CardCondition condition : values()) {
            // values() liefert alle Enum‑Konstanten (GEM_MINT, MINT, NEAR_MINT, usw.).
            // Schleife über alle möglichen Zustände.
            if (condition.name().equalsIgnoreCase(normalizedInput)
                    || condition.displayName.equalsIgnoreCase(input.trim())
                    || condition.shortName.equalsIgnoreCase(input.trim())
                    || condition.getFormattedDisplay().equalsIgnoreCase(input.trim())) {
                // Prüft, ob irgendein Kriterium zutrifft:
                // 1. Enum‑Name (z.B. "GEM_MINT") passt zur normalisierten Eingabe.
                // 2. Langname (z.B. "Gem Mint") passt zur Eingabe.
                // 3. Kurzname (z.B. "GM") passt zur Eingabe.
                // 4. formatierte Anzeige (z.B. "Gem Mint (GM)") passt zur Eingabe.

                return condition;
                // Wenn ja, gibt die passende CardCondition zurück.
            }
        }

        throw new IllegalArgumentException("Ungültiger Zustand.");
        // Wenn kein Zustand passt, wird eine Ausnahme geworfen.
    }

    @Override
    public String toString() {
        // Überschreibt toString(), damit die Enum‑Konstante als Text dargestellt wird.
        return getFormattedDisplay();
        // Gibt z.B. "Gem Mint (GM)" zurück, statt nur den Enum‑Namen.
    }
}