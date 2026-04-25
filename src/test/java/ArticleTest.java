import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class ArticleTest {

    @Test
    void increaseStock_shouldThrowExceptionWhenAmountIsZeroOrNegative() {
        Article article = new Article("Laptop", "A103", new BigDecimal("999.99"), 10, 2);

        assertThrows(IllegalArgumentException.class, () -> article.increaseStock(0));
        assertThrows(IllegalArgumentException.class, () -> article.increaseStock(-5));
    }

    @Test
    void decreaseStock_shouldReduceStockWhenAmountIsValid() {
        Article article = new Article("Monitor", "A102", new BigDecimal("199.99"), 10, 2);

        article.decreaseStock(4);

        assertEquals(6, article.getStock());
    }

    @Test
    void increaseStock_shouldIncreaseStockByAmount() {
        Article article = new Article("Tastatur", "A100", new BigDecimal("49.99"), 10, 2);

        article.increaseStock(5);

        assertEquals(15, article.getStock());
    }

    @Test
    void decreaseStock_shouldThrowExceptionWhenAmountIsTooHigh() {
        Article article = new Article("Maus", "A101", new BigDecimal("19.99"), 10, 2);

        assertThrows(IllegalArgumentException.class, () -> article.decreaseStock(20));
    }

    @Test
    void hasLowStock_shouldReturnTrueWhenStockIsLessThanOrEqualToMinimumStock() {
        Article article = new Article("Kabel", "A104", new BigDecimal("9.99"), 2, 2);

        assertTrue(article.hasLowStock());
    }

    @Test
    void hasLowStock_shouldReturnFalseWhenStockIsAboveMinimumStock() {
        Article article = new Article("Adapter", "A105", new BigDecimal("14.99"), 5, 2);

        assertFalse(article.hasLowStock());
    }

    @Test
    void getArticleType_shouldReturnArtikel() {
        Article article = new Article("Headset", "A106", new BigDecimal("79.99"), 4, 1);

        assertEquals("Artikel", article.getArticleType());
    }
}