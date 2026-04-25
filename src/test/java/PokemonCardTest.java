import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class PokemonCardTest {

    @Test
    void constructor_shouldCreatePokemonCardSuccessfully() {
        PokemonCard pokemonCard = new PokemonCard(
                "Pikachu",
                "SET-001",
                new BigDecimal("99.99"),
                3,
                "Base Set",
                1999,
                CardCondition.NEAR_MINT,
                HoloType.HOLO,
                "Deutsch",
                true
        );

        assertEquals("Pikachu", pokemonCard.getArticleName());
        assertEquals("SET-001", pokemonCard.getArticleID());
        assertEquals(new BigDecimal("99.99"), pokemonCard.getArticlePrice());
        assertEquals(3, pokemonCard.getStock());
        assertEquals("Base Set", pokemonCard.getSetName());
        assertEquals(1999, pokemonCard.getReleaseYear());
        assertEquals(CardCondition.NEAR_MINT, pokemonCard.getCondition());
        assertEquals(HoloType.HOLO, pokemonCard.getHoloType());
        assertEquals("Deutsch", pokemonCard.getLanguage());
        assertEquals(true, pokemonCard.isFirstEdition());
    }

    @Test
    void constructor_shouldThrowExceptionWhenSetNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new PokemonCard(
                "Pikachu",
                "SET-001",
                new BigDecimal("99.99"),
                3,
                "",
                1999,
                CardCondition.NEAR_MINT,
                HoloType.HOLO,
                "Deutsch",
                true
        ));
    }

    @Test
    void constructor_shouldThrowExceptionWhenConditionIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new PokemonCard(
                "Pikachu",
                "SET-001",
                new BigDecimal("99.99"),
                3,
                "Base Set",
                1999,
                null,
                HoloType.HOLO,
                "Deutsch",
                true
        ));
    }

    @Test
    void constructor_shouldThrowExceptionWhenHoloTypeIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new PokemonCard(
                "Pikachu",
                "SET-001",
                new BigDecimal("99.99"),
                3,
                "Base Set",
                1999,
                CardCondition.NEAR_MINT,
                null,
                "Deutsch",
                true
        ));
    }

    @Test
    void constructor_shouldThrowExceptionWhenLanguageIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new PokemonCard(
                "Pikachu",
                "SET-001",
                new BigDecimal("99.99"),
                3,
                "Base Set",
                1999,
                CardCondition.NEAR_MINT,
                HoloType.HOLO,
                "",
                true
        ));
    }

    @Test
    void hasLowStock_shouldAlwaysReturnFalse() {
        PokemonCard pokemonCard = new PokemonCard(
                "Charizard",
                "SET-002",
                new BigDecimal("499.99"),
                1,
                "Base Set",
                1999,
                CardCondition.EXCELLENT,
                HoloType.HOLO,
                "Englisch",
                false
        );

        assertFalse(pokemonCard.hasLowStock());
    }

    @Test
    void increaseStock_shouldIncreaseStockByAmount() {
        PokemonCard pokemonCard = new PokemonCard(
                "Mewtwo",
                "SET-003",
                new BigDecimal("149.99"),
                2,
                "Base Set",
                1999,
                CardCondition.NEAR_MINT,
                HoloType.REVERSE_HOLO,
                "Deutsch",
                false
        );

        pokemonCard.increaseStock(3);

        assertEquals(5, pokemonCard.getStock());
    }

    @Test
    void decreaseStock_shouldReduceStockWhenAmountIsValid() {
        PokemonCard pokemonCard = new PokemonCard(
                "Blastoise",
                "SET-004",
                new BigDecimal("299.99"),
                5,
                "Base Set",
                1999,
                CardCondition.GOOD,
                HoloType.HOLO,
                "Japanisch",
                true
        );

        pokemonCard.decreaseStock(2);

        assertEquals(3, pokemonCard.getStock());
    }

    @Test
    void getArticleType_shouldReturnPokemonCard() {
        PokemonCard pokemonCard = new PokemonCard(
                "Venusaur",
                "SET-005",
                new BigDecimal("199.99"),
                2,
                "Base Set",
                1999,
                CardCondition.MINT,
                HoloType.FULL_ART,
                "Deutsch",
                false
        );

        assertEquals("PokemonCard", pokemonCard.getArticleType());
    }

    @Test
    void cardCondition_fromInput_shouldIgnoreCaseAndAcceptDisplayName() {
        assertEquals(CardCondition.NEAR_MINT, CardCondition.fromInput("near mint"));
        assertEquals(CardCondition.NEAR_MINT, CardCondition.fromInput("Near Mint"));
        assertEquals(CardCondition.NEAR_MINT, CardCondition.fromInput("NEAR MINT"));
    }

    @Test
    void cardCondition_fromInput_shouldAcceptShortName() {
        assertEquals(CardCondition.GEM_MINT, CardCondition.fromInput("GM"));
        assertEquals(CardCondition.NEAR_MINT, CardCondition.fromInput("nm"));
        assertEquals(CardCondition.POOR, CardCondition.fromInput("po"));
    }

    @Test
    void holoType_fromInput_shouldIgnoreCase() {
        assertEquals(HoloType.HOLO, HoloType.fromInput("holo"));
        assertEquals(HoloType.REVERSE_HOLO, HoloType.fromInput("reverse holo"));
        assertEquals(HoloType.FULL_ART, HoloType.fromInput("FULL_ART"));
        assertEquals(HoloType.GOLD, HoloType.fromInput("gold"));
    }

    @Test
    void cardCondition_fromInput_shouldThrowExceptionForInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> CardCondition.fromInput("kaputt"));
    }

    @Test
    void holoType_fromInput_shouldThrowExceptionForInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> HoloType.fromInput("sparkle"));
    }
}