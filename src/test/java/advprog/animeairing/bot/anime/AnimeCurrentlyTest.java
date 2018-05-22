package advprog.animeairing.bot.anime;

import  static  org.junit.Assert.assertEquals;
import  static  org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class AnimeCurrentlyTest {
    AnimeCurrently animeCurrently;
    String urlApi = "https://myanimelist.net/api/anime/search.xml?q=";
    String anime = "naruto";

    @Before
    public void setUp() {
        animeCurrently = new AnimeCurrently(anime);
    }

    @Test
    public void testGetUrl() {
        assertEquals(urlApi, animeCurrently.getUrlApi());
    }

    @Test
    public void testGetAnime() {
        assertEquals(anime, animeCurrently.getAnime());
    }

    @Test
    public void testInfoAiring() {
        String expected = animeCurrently.infoAiring();
        assertEquals("Naruto has finished airing at 2007-02-08", expected);
    }

    @Test
    public void testGetCurrentAiring() {
        ArrayList<AnimeAiring> expected = animeCurrently.getCurrentAiring();
        assertNotNull(expected);
    }
}
