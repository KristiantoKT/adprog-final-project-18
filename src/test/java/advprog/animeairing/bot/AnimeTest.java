package advprog.animeairing.bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AnimeTest {
    Anime anime;

    @BeforeEach
    void setUp() {
        anime = new Anime("Title", "1");
    }

    @Test
    void testGetTitle() {
        assertEquals("Title", anime.getTitle());
    }

    @Test
    void testSetTitle() {
        anime.setTitle("changeTitle");
        assertEquals("changeTitle", anime.getTitle());
    }

    @Test
    void testGetEpisode() {
        assertEquals("1", anime.getEpisode());
    }

    @Test
    void testSetEpisode() {
        anime.setEpisode("2");
        assertEquals("2", anime.getEpisode());
    }

}
