import java.math.BigDecimal;

public class PokemonCard extends BaseArticle {

    private final String setName;
    private final int releaseYear;
    private final CardCondition condition;
    private final HoloType holoType;
    private final String language;
    private final boolean firstEdition;

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
        super(articleName, articleID, articlePrice, stock);

        if (setName == null || setName.trim().isEmpty()) {
            throw new IllegalArgumentException("Setname darf nicht leer sein.");
        }

        if (releaseYear < 1900) {
            throw new IllegalArgumentException("Jahr ist ungültig.");
        }

        if (condition == null) {
            throw new IllegalArgumentException("Zustand darf nicht null sein.");
        }

        if (holoType == null) {
            throw new IllegalArgumentException("HoloType darf nicht null sein.");
        }

        if (language == null || language.trim().isEmpty()) {
            throw new IllegalArgumentException("Sprache darf nicht leer sein.");
        }

        this.setName = setName;
        this.releaseYear = releaseYear;
        this.condition = condition;
        this.holoType = holoType;
        this.language = language.trim();
        this.firstEdition = firstEdition;
    }

    public String getSetName() {
        return setName;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public CardCondition getCondition() {
        return condition;
    }

    public HoloType getHoloType() {
        return holoType;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isFirstEdition() {
        return firstEdition;
    }

    @Override
    public boolean hasLowStock() {
        return false;
    }

    @Override
    public String getArticleType() {
        return "PokemonCard";
    }

    @Override
    public String toString() {
        return "PokemonCard{" +
                "articleName='" + getArticleName() + '\'' +
                ", articleID='" + getArticleID() + '\'' +
                ", articlePrice=" + getArticlePrice() +
                ", stock=" + getStock() +
                ", setName='" + setName + '\'' +
                ", releaseYear=" + releaseYear +
                ", condition=" + condition +
                ", holoType=" + holoType +
                ", language='" + language + '\'' +
                ", firstEdition=" + firstEdition +
                '}';
    }
}