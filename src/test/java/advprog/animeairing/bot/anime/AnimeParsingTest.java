package advprog.animeairing.bot.anime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class AnimeParsingTest {
    AnimeParsing animeParsing;
    String animeTitle = "naruto";
    String url = "http://advpro:Advprobeb@myanimelist.net/api/anime/search.xml?q=";

    @Before
    public void setUp() {
        animeParsing = new AnimeParsing(animeTitle);
    }


    @Test
    public void testUrlNotFound() {
        new AnimeAiringToday("https://haduh.ac.id/");
    }

    @Test
    public void testGetUrl() {
        assertEquals(url, animeParsing.getUrl());
    }


}
