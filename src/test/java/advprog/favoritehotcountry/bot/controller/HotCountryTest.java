package advprog.favoritehotcountry.bot.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class HotCountryTest {
    HotCountry hotCountry;


    @Before
    public void SetUp() {
        hotCountry = new HotCountry("https://www.billboard.com/charts/tropical-songs");
    }

    @Test
    public void urlNotValidTest() {
        new HotCountry("https://haduh.ac.id/");
    }

    @Test
    public void infoArtistTest() {
        String expected = hotCountry.infoArtist();
        assertTrue(expected.contains("Famous"));
    }

    @Test
    public void getUrlTest() {

        assertEquals("https://www.billboard.com/charts/tropical-songs", hotCountry.getUrl());
    }

}
