package advprog.animeairing.bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AnimeTest {
    Anime anime;

    @BeforeEach
    void seUp() {
        anime = new Anime("Title", 1);
    }

    @Test
    void testGetTitle() {
        String title = anime.getTitle();
        assertEquals(title, anime.getTitle());
    }

    @Test
    void testSetTitle() {
        anime.setTitle("changeTitle");
        assertEquals("changeTitle", anime.getTitle());
    }

    @Test
    void testGetEpisode() {
        int episode = anime.getEpisode()
        assertEquals("1", anime.getEpisode());
    }

    @Test
    void testSetEpisode() {
        anime.setEpisode(2)
        assertEquals(2, anime.getEpisode());
    }

}
