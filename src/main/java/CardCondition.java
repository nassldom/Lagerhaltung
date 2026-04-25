public enum CardCondition {
    GEM_MINT("Gem Mint", "GM"),
    MINT("Mint", "M"),
    NEAR_MINT("Near Mint", "NM"),
    EXCELLENT("Excellent", "EX"),
    GOOD("Good", "GD"),
    LIGHT_PLAYED("Light Played", "LP"),
    PLAYED("Played", "PL"),
    POOR("Poor", "PO");

    private final String displayName;
    private final String shortName;

    CardCondition(String displayName, String shortName) {
        this.displayName = displayName;
        this.shortName = shortName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFormattedDisplay() {
        return displayName + " (" + shortName + ")";
    }

    public static CardCondition fromInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Zustand darf nicht leer sein.");
        }

        String normalizedInput = input.trim()
                .replace("-", "_")
                .replace(" ", "_");

        for (CardCondition condition : values()) {
            if (condition.name().equalsIgnoreCase(normalizedInput)
                    || condition.displayName.equalsIgnoreCase(input.trim())
                    || condition.shortName.equalsIgnoreCase(input.trim())
                    || condition.getFormattedDisplay().equalsIgnoreCase(input.trim())) {
                return condition;
            }
        }

        throw new IllegalArgumentException("Ungültiger Zustand.");
    }

    @Override
    public String toString() {
        return getFormattedDisplay();
    }
}