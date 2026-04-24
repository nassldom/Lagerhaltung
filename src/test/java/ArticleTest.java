import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class ArticleTest {
    @Test
    void increaseStock_shouldThrowExceptionWhenAmountIsZeroOrNegative() {
    Article article = new Article("Laptop", "A103", new BigDecimal("999.99"), 10, 2);

    assertThrows(IllegalArgumentException.class, () -> {
        article.increaseStock(0);
    });
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

        assertThrows(IllegalArgumentException.class, () -> {
            article.decreaseStock(20);
        });
    }
}