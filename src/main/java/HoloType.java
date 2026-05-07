/*
HoloType ist eine Enum für Holo‑Varianten von Pokémon‑Karten. Sie speichert einen lesbaren Anzeigetext (displayName)
und bietet eine Methode fromInput(String), um aus Texteingaben wie "Full Art" oder "Full-Art" die passende Holo‑Variante
zu finden. toString() gibt einfach den Anzeigetext zurück, sodass die Konsolenausgabe lesbar ist.
 */

public enum HoloType {
// Definiert eine öffentliche Enumeration namens HoloType.
// Sie beschreibt verschiedene Holo‑Typen (Holografien) von Karten.

    NONE("None"),
// Konstante: kein Holo (kein Glanz).
// Übergibt den Anzeigetext "None" an den Konstruktor.

    HOLO("Holo"),
// Konstante: normales Holo.

    REVERSE_HOLO("Reverse Holo"),
// Konstante: Reverse Holo.

    FULL_ART("Full Art"),
// Konstante: Full‑Art‑Karte.

    GOLD("Gold"),
// Konstante: Gold‑Holo (z.B. Gold‑Folienkarte).

    RAINBOW("Rainbow");
// Konstante: Rainbow‑Holo.
// Ende der Konstanten‑Deklaration.

    private final String displayName;
// Privates, unveränderliches Attribut für den Langnamen des HoloTyps (z.B. "Full Art").

    HoloType(String displayName) {
// Enum‑Konstruktor: Wird für jede Konstante beim Laden der Klasse aufgerufen.

        this.displayName = displayName;
// Speichert den übergebenen Anzeigetext im Attribut.
    }

    public String getDisplayName() {
// Getter‑Methode für displayName.
// Gibt den langen Namen des HoloTyps zurück.

        return displayName;
    }

    public static HoloType fromInput(String input) {
// Statische Methode, um aus einer Zeichenkette den passenden HoloType zu ermitteln.

        if (input == null || input.trim().isEmpty()) {
// Prüft, ob die Eingabe null oder (nach Trimmen) leer ist.

            throw new IllegalArgumentException("HoloType darf nicht leer sein.");
// Ungültige Eingabe führt zu einer Ausnahme.
        }

        String normalizedInput = input.trim()
                .replace("-", "_")
                .replace(" ", "_");
// Bereinigt die Eingabe:
// - entfernt Leerzeichen vor/hinten
// - ersetzt "-" durch "_"
// Damit passen z.B. "Reverse Holo" oder "Reverse-Holo" zum Namen REVERSE_HOLO.

        for (HoloType holoType : values()) {
// values() liefert alle Enum‑Konstanten (NONE, HOLO, REVERSE_HOLO, usw.).
// Schleife über alle Typen.

            if (holoType.name().equalsIgnoreCase(normalizedInput)
                    || holoType.displayName.equalsIgnoreCase(input.trim())) {
// Prüft, ob:
// 1. der Enum‑Name (z.B. "REVERSE_HOLO") zur normalisierten Eingabe passt
//    oder
// 2. der Anzeigetext (z.B. "Reverse Holo") exakt zur Eingabe passt.

                return holoType;
// Gibt die passende HoloType‑Konstante zurück.
            }
        }

        throw new IllegalArgumentException("Ungültiger HoloType.");
// Wenn kein Typ passt, wird eine Ausnahme geworfen.
    }

    @Override
    public String toString() {
// Überschreibt toString(), damit die Enum‑Konstante als Text dargestellt wird.

        return displayName;
// Gibt z.B. "Full Art", "Reverse Holo" usw. zurück,
// statt nur den Enum‑Namen.
    }
}