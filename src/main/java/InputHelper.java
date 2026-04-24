import java.math.BigDecimal;
import java.util.Scanner;

public class InputHelper {

    public static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }

            System.out.println("Eingabe darf nicht leer sein.");
        }
    }

    public static int readNonNegativeInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);

            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();

                if (value >= 0) {
                    return value;
                }

                System.out.println("Die Zahl darf nicht negativ sein.");
            } else {
                System.out.println("Ungültige Eingabe. Bitte eine ganze Zahl eingeben.");
                scanner.nextLine();
            }
        }
    }

    public static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);

            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();

                if (value > 0) {
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
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try {
                BigDecimal value = new BigDecimal(input);

                if (value.compareTo(BigDecimal.ZERO) >= 0) {
                    return value;
                }

                System.out.println("Die Zahl darf nicht negativ sein.");
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine gültige Zahl eingeben.");
            }
        }
    }
}