import java.math.BigDecimal;

public class Article extends BaseArticle {

    private int minimumStock;

    public Article(String articleName, String articleID, BigDecimal articlePrice, int stock, int minimumStock) {
        super(articleName, articleID, articlePrice, stock);

        if (minimumStock < 0) {
            throw new IllegalArgumentException("Mindestlagerbestand darf nicht negativ sein.");
        }

        this.minimumStock = minimumStock;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    @Override
    public boolean hasLowStock() {
        return getStock() <= minimumStock;
    }

    @Override
    public String getArticleType() {
        return "Artikel";
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleName='" + getArticleName() + '\'' +
                ", articleID='" + getArticleID() + '\'' +
                ", articlePrice=" + getArticlePrice() +
                ", stock=" + getStock() +
                ", minimumStock=" + minimumStock +
                '}';
    }
}