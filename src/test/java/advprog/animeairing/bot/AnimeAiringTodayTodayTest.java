package advprog.animeairing.bot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class AnimeAiringTodayTodayTest {
    AnimeAiringToday animeAiringToday;
    String url = "";

    @Before
    public void setUp() {
        animeAiringToday = new AnimeAiringToday(url);
    }

    @Test
    public void testUrlNotFound() {
        new AnimeAiringToday("");
    }

    @Test
    public void testGetUrlAnimeAiringToday() {
        assertEquals("", animeAiringToday);
    }

    @Test
    public void testGetAnimeAiringToday() {
        assertTrue(true);
    }

    @Test
    public void testGetAnimeAiringTodayList() {
        assertEquals("", animeAiringToday);
    }
}
