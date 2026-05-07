/*
InputHelper bietet wiederverwendbare, sichere Methoden, um aus der Konsole:
nicht leere Strings,
nicht negative Integers,
positive Integers und
nicht negative BigDecimal‑Zahlen zu lesen. Jede Methode prüft die Eingabe und fragt so lange erneut, bis eine gültige
Zahl bzw. ein gültiger Text eingegeben wird.
 */

import java.math.BigDecimal;
// Importiert die Klasse BigDecimal für exakte Dezimalzahlen (z.B. Preise).

import java.util.Scanner;
// Importiert Scanner, um Nutzereingaben aus der Konsole zu lesen.

public class InputHelper {

// Hilfsklasse mit statischen Methoden, um typische Konsolen‑Eingaben sicher zu lesen.
// Kann wiederholt verwendet werden, ohne ein Objekt zu erstellen.

    public static String readNonEmptyString(Scanner scanner, String prompt) {
// Statische Methode, die eine nicht leere Zeichenkette von der Konsole einliest.

        while (true) {
// Endlosschleife, bis eine gültige Eingabe vorliegt.

            System.out.print(prompt);
// Gibt den Eingabe‑Prompt auf die Konsole aus.

            String input = scanner.nextLine();
// Liest eine ganze Zeile als String.

            if (input != null && !input.trim().isEmpty()) {
// Prüft, ob input nicht null ist und nach Leerzeichenentfernung nicht leer.

                return input.trim();
// Gibt den bereinigten String (ohne führende/abschließende Leerzeichen) zurück.
            }

            System.out.println("Eingabe darf nicht leer sein.");
// Fehlermeldung, wenn die Eingabe leer oder nur Leerzeichen ist.
        }
    }

    public static int readNonNegativeInt(Scanner scanner, String prompt) {
// Statische Methode, um eine nicht negative ganze Zahl (≥ 0) einzulesen.

        while (true) {
// Endlosschleife, bis eine gültige, nicht negative Zahl eingegeben wurde.

            System.out.print(prompt);
// Gibt den Eingabe‑Prompt aus.

            if (scanner.hasNextInt()) {
// Prüft, ob die nächste Eingabe eine ganze Zahl ist.

                int value = scanner.nextInt();
// Liest den Integer‑Wert.

                scanner.nextLine();
// „Frisst“ den restlichen Zeilenumbruch nach der Zahl,
// damit der nächste Scanner‑Aufruf nicht gestört wird.

                if (value >= 0) {
// Prüft, ob der Wert nicht negativ ist.

                    return value;
// Gibt die gültige, nicht negative Zahl zurück.
                }

                System.out.println("Die Zahl darf nicht negativ sein.");
// Fehlermeldung, wenn der Wert negativ ist.
            } else {
// Wenn hasNextInt() false ist, ist die Eingabe keine ganze Zahl.

                System.out.println("Ungültige Eingabe. Bitte eine ganze Zahl eingeben.");
                scanner.nextLine();
// Liest die ungültige Zeile komplett weg, bevor die Schleife neu startet.
            }
        }
    }

    public static int readPositiveInt(Scanner scanner, String prompt) {
// Statische Methode, um eine **positive** ganze Zahl (> 0) einzulesen.

        while (true) {
            System.out.print(prompt);

            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();

                if (value > 0) {
// Prüft, ob die Zahl größer als 0 ist.

                    return value;
                }

                System.out.println("Die Zahl muss größer als 0 sein.");
            } else {
                System.out.println("Ungültige Eingabe. Bitte eine ganze Zahl eingeben.");
                scanner.nextLine();
            }
        }
    }

    public static BigDecimal readNonNegativeBigDecimal(Scanner scanner, String prompt) {
// Statische Methode, um eine nicht negative BigDecimal (≥ 0) einzulesen.

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
// Liest die Eingabe als String, damit z.B. "12.99" korrekt geparst werden kann.

            try {
                BigDecimal value = new BigDecimal(input);
// Versucht, den String in ein BigDecimal umzuwandeln.

                if (value.compareTo(BigDecimal.ZERO) >= 0) {
// Prüft, ob value ≥ 0 (nicht negativ).

                    return value;
                }

                System.out.println("Die Zahl darf nicht negativ sein.");
            } catch (NumberFormatException e) {
// Fängt Fehler ab, wenn der String keine gültige Zahl ist (z.B. "abc").

                System.out.println("Ungültige Eingabe. Bitte eine gültige Zahl eingeben.");
            }
        }
    }
}