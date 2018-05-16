package advprog.animeairing.bot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class AnimeAiringTodayTodayTest {
    AnimeAiringToday animeAiringToday;
    String url = "https://www.livechart.me/schedule/tv";

    @Before
    public void setUp() {
        animeAiringToday = new AnimeAiringToday(url);
    }

    @Test
    public void testUrlNotFound() {
        new AnimeAiringToday("https://haduh.ac.id/");
    }

    @Test
    public void testGetUrlAnimeAiringToday() {
        assertEquals(url, animeAiringToday.getAnimeUrlAiringToday());
    }

    @Test
    public void testGetAnimeAiringToday() {
        String expected = animeAiringToday.listAiringToday();
        System.out.println(expected);
        assertNotNull(expected);
    }

    @Test
    public void testGetAnimeAiringTodayList() {
        ArrayList<Anime> listAiringToday = animeAiringToday.getAiringToday();
        assertNotNull(listAiringToday);
    }
}
