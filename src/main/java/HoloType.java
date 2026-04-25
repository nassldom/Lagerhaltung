public enum HoloType {
    NONE("None"),
    HOLO("Holo"),
    REVERSE_HOLO("Reverse Holo"),
    FULL_ART("Full Art"),
    GOLD("Gold"),
    RAINBOW("Rainbow");

    private final String displayName;

    HoloType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static HoloType fromInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("HoloType darf nicht leer sein.");
        }

        String normalizedInput = input.trim()
                .replace("-", "_")
                .replace(" ", "_");

        for (HoloType holoType : values()) {
            if (holoType.name().equalsIgnoreCase(normalizedInput)
                    || holoType.displayName.equalsIgnoreCase(input.trim())) {
                return holoType;
            }
        }

        throw new IllegalArgumentException("Ungültiger HoloType.");
    }

    @Override
    public String toString() {
        return displayName;
    }
}