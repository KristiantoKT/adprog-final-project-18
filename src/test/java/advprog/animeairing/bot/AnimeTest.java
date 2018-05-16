package advprog.animeairing.bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AnimeTest {
    Anime anime;

    @BeforeEach
    void seUp() {
        anime = new Anime();
    }

    @Test
    void testGetTitle() {
        assertEquals("", anime);
    }

    @Test
    void testSetTitle() {
        assertEquals("", anime);
    }

    @Test
    void testGetEpisode() {
        assertEquals("", anime);
    }

    @Test
    void testSetEpisode() {
        assertEquals("", anime);
    }

}
