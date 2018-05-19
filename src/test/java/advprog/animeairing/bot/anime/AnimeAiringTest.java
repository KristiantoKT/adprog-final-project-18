package advprog.animeairing.bot.anime;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnimeAiringTest {
    AnimeAiring animeAiring;


    @BeforeEach
    void setUp() {
        animeAiring = new AnimeAiring("Title", "Airing", "2018-05-06", "2018-06-07");
    }

    @Test
    void testGetTitle() {
        assertEquals("Title", animeAiring.getTitle());
    }

    @Test
    void testSetTitle() {
        animeAiring.setTitle("changeTitle");
        assertEquals("changeTitle", animeAiring.getTitle());
    }

    @Test
    void testGetStatus() {
        assertEquals("Airing", animeAiring.getStatus());
    }

    @Test
    void testSetStatus() {
        animeAiring.setStatus("Not Airing");
        assertEquals("Not Airing", animeAiring.getStatus());
    }

    @Test
    void  testGetStartDate() {
        assertEquals("2018-05-06", animeAiring.getStartDate());
    }

    @Test
    void testSetStartDate() {
        animeAiring.setStartDate("2018-06-07");
        assertEquals("2018-06-07", animeAiring.getStartDate());
    }

    @Test
    void  testGetEndDate() {
        assertEquals("2018-06-07", animeAiring.getEndDate());
    }

    @Test
    void testSetEndDate() {
        animeAiring.setEndDate("2018-07-08");
        assertEquals("2018-07-08", animeAiring.getEndDate());
    }
}
