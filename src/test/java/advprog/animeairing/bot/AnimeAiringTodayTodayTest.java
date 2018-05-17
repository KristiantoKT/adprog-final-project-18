package advprog.animeairing.bot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;



public class AnimeAiringTodayTodayTest {
    AnimeAiringToday animeAiringToday;
    String url = "https://www.livechart.me/schedule/tv?layout=compact";

    @Before
    public void setUp() {
        animeAiringToday = new AnimeAiringToday(url);
    }


    @Test
    public void testUrlNotFound() {
        new AnimeAiringToday("https://haduh.ac.id/");
    }

    @Test
    public void testGetUrl() {
        assertEquals(url, animeAiringToday.getUrlAiringToday());
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
