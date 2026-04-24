import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {

    @Test
    void addArticle_shouldAddArticleSuccessfully() {
        Inventory inventory = new Inventory();
        Article article = new Article("Laptop", "A100", new BigDecimal("999.99"), 10, 3);

        inventory.addArticle(article);

        assertEquals(1, inventory.getAllArticles().size());
        assertEquals(article, inventory.getAllArticles().get(0));
    }

    @Test
    void addArticle_shouldThrowExceptionWhenArticleIsNull() {
        Inventory inventory = new Inventory();

        assertThrows(IllegalArgumentException.class, () -> inventory.addArticle(null));
    }

    @Test
    void addArticle_shouldThrowExceptionWhenArticleIdAlreadyExists() {
        Inventory inventory = new Inventory();
        Article article1 = new Article("Laptop", "A100", new BigDecimal("999.99"), 10, 3);
        Article article2 = new Article("Monitor", "A100", new BigDecimal("199.99"), 5, 2);

        inventory.addArticle(article1);

        assertThrows(IllegalArgumentException.class, () -> inventory.addArticle(article2));
    }

    @Test
    void getArticleByNumber_shouldReturnArticleWhenFound() {
        Inventory inventory = new Inventory();
        Article article = new Article("Laptop", "A100", new BigDecimal("999.99"), 10, 3);

        inventory.addArticle(article);

        Article foundArticle = inventory.getArticleByNumber("A100");

        assertNotNull(foundArticle);
        assertEquals(article, foundArticle);
    }

    @Test
    void getArticleByNumber_shouldReturnNullWhenNotFound() {
        Inventory inventory = new Inventory();

        Article foundArticle = inventory.getArticleByNumber("UNKNOWN");

        assertNull(foundArticle);
    }

    @Test
    void getArticleByName_shouldReturnArticleWhenFound() {
        Inventory inventory = new Inventory();
        Article article = new Article("Laptop", "A100", new BigDecimal("999.99"), 10, 3);

        inventory.addArticle(article);

        Article foundArticle = inventory.getArticleByName("Laptop");

        assertNotNull(foundArticle);
        assertEquals(article, foundArticle);
    }

    @Test
    void getArticleByName_shouldReturnNullWhenNotFound() {
        Inventory inventory = new Inventory();

        Article foundArticle = inventory.getArticleByName("Drucker");

        assertNull(foundArticle);
    }

    @Test
    void removeArticle_shouldReturnTrueWhenArticleExists() {
        Inventory inventory = new Inventory();
        Article article = new Article("Laptop", "A100", new BigDecimal("999.99"), 10, 3);

        inventory.addArticle(article);

        boolean removed = inventory.removeArticle("A100");

        assertTrue(removed);
        assertEquals(0, inventory.getAllArticles().size());
    }

    @Test
    void removeArticle_shouldReturnFalseWhenArticleDoesNotExist() {
        Inventory inventory = new Inventory();

        boolean removed = inventory.removeArticle("UNKNOWN");

        assertFalse(removed);
    }

    @Test
    void increaseStock_shouldIncreaseStockSuccessfully() {
        Inventory inventory = new Inventory();
        Article article = new Article("Laptop", "A100", new BigDecimal("999.99"), 10, 3);

        inventory.addArticle(article);
        inventory.increaseStock("A100", 5);

        assertEquals(15, article.getStock());
    }

    @Test
    void increaseStock_shouldCreateStockMovement() {
        Inventory inventory = new Inventory();
        Article article = new Article("Laptop", "A100", new BigDecimal("999.99"), 10, 3);

        inventory.addArticle(article);
        inventory.increaseStock("A100", 5);

        assertEquals(1, inventory.getAllMovements().size());
        assertEquals("A100", inventory.getAllMovements().get(0).getArticleID());
        assertEquals("Laptop", inventory.getAllMovements().get(0).getArticleName());
        assertEquals(MovementType.IN, inventory.getAllMovements().get(0).getMovementType());
        assertEquals(5, inventory.getAllMovements().get(0).getAmount());
    }

    @Test
    void increaseStock_shouldThrowExceptionWhenArticleDoesNotExist() {
        Inventory inventory = new Inventory();

        assertThrows(IllegalArgumentException.class, () -> inventory.increaseStock("UNKNOWN", 5));
    }

    @Test
    void decreaseStock_shouldDecreaseStockSuccessfully() {
        Inventory inventory = new Inventory();
        Article article = new Article("Laptop", "A100", new BigDecimal("999.99"), 10, 3);

        inventory.addArticle(article);
        inventory.decreaseStock("A100", 4);

        assertEquals(6, article.getStock());
    }

    @Test
    void decreaseStock_shouldCreateStockMovement() {
        Inventory inventory = new Inventory();
        Article article = new Article("Laptop", "A100", new BigDecimal("999.99"), 10, 3);

        inventory.addArticle(article);
        inventory.decreaseStock("A100", 4);

        assertEquals(1, inventory.getAllMovements().size());
        assertEquals("A100", inventory.getAllMovements().get(0).getArticleID());
        assertEquals("Laptop", inventory.getAllMovements().get(0).getArticleName());
        assertEquals(MovementType.OUT, inventory.getAllMovements().get(0).getMovementType());
        assertEquals(4, inventory.getAllMovements().get(0).getAmount());
    }

    @Test
    void decreaseStock_shouldThrowExceptionWhenArticleDoesNotExist() {
        Inventory inventory = new Inventory();

        assertThrows(IllegalArgumentException.class, () -> inventory.decreaseStock("UNKNOWN", 5));
    }

    @Test
    void getLowStockItems_shouldReturnOnlyCriticalArticles() {
        Inventory inventory = new Inventory();

        Article article1 = new Article("Laptop", "A100", new BigDecimal("999.99"), 2, 3);
        Article article2 = new Article("Monitor", "A101", new BigDecimal("199.99"), 10, 3);
        Article article3 = new Article("Tastatur", "A102", new BigDecimal("49.99"), 1, 2);

        inventory.addArticle(article1);
        inventory.addArticle(article2);
        inventory.addArticle(article3);

        List<Article> lowStockItems = inventory.getLowStockItems();

        assertEquals(2, lowStockItems.size());
        assertTrue(lowStockItems.contains(article1));
        assertTrue(lowStockItems.contains(article3));
        assertFalse(lowStockItems.contains(article2));
    }

    @Test
    void getMovementsByArticleID_shouldReturnOnlyMatchingMovements() {
        Inventory inventory = new Inventory();

        Article article1 = new Article("Tastatur", "A400", new BigDecimal("49.99"), 10, 2);
        Article article2 = new Article("Maus", "A401", new BigDecimal("19.99"), 8, 2);

        inventory.addArticle(article1);
        inventory.addArticle(article2);

        inventory.increaseStock("A400", 5);
        inventory.decreaseStock("A400", 3);
        inventory.increaseStock("A401", 2);

        List<StockMovement> movementsForA400 = inventory.getMovementsByArticleID("A400");

        assertEquals(2, movementsForA400.size());
        assertEquals("A400", movementsForA400.get(0).getArticleID());
        assertEquals("A400", movementsForA400.get(1).getArticleID());
    }

    @Test
    void getArticleCount_shouldReturnNumberOfArticles() {
        Inventory inventory = new Inventory();

        inventory.addArticle(new Article("Laptop", "A100", new BigDecimal("999.99"), 10, 3));
        inventory.addArticle(new Article("Monitor", "A101", new BigDecimal("199.99"), 5, 2));
        inventory.addArticle(new Article("Tastatur", "A102", new BigDecimal("49.99"), 8, 2));

        assertEquals(3, inventory.getArticleCount());
    }

    @Test
    void getTotalStock_shouldReturnSumOfAllArticleStocks() {
        Inventory inventory = new Inventory();

        inventory.addArticle(new Article("Laptop", "A100", new BigDecimal("999.99"), 10, 3));
        inventory.addArticle(new Article("Monitor", "A101", new BigDecimal("199.99"), 5, 2));
        inventory.addArticle(new Article("Tastatur", "A102", new BigDecimal("49.99"), 8, 2));

        assertEquals(23, inventory.getTotalStock());
    }

    @Test
    void getLowStockCount_shouldReturnNumberOfCriticalArticles() {
        Inventory inventory = new Inventory();

        inventory.addArticle(new Article("Laptop", "A100", new BigDecimal("999.99"), 2, 3));
        inventory.addArticle(new Article("Monitor", "A101", new BigDecimal("199.99"), 5, 2));
        inventory.addArticle(new Article("Tastatur", "A102", new BigDecimal("49.99"), 1, 2));

        assertEquals(2, inventory.getLowStockCount());
    }
}