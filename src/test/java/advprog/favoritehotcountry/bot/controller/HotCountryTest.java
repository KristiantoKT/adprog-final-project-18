package advprog.favoritehotcountry.bot.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;



public class HotCountryTest {
    HotCountry hotCountry;
    String url = "https://www.billboard.com/charts/country-songs";
    String artist = "Mason Ramsey";


    @Before
    public void setUp() {

        hotCountry = new HotCountry(artist, url);
    }

    @Test
    public void urlNotValidTest() {

        new HotCountry("budi","https://haduh.ac.id/");
    }

    @Test
    public void infoArtistTest() {

        String expected = hotCountry.infoArtist();
        assertTrue(expected.contains("Famous"));
        assertEquals("Artist : Mason Ramsey\n"
                + "Title Song : Famous\n"
                + "Position : 4", expected);
    }

    @Test
    public void getUrlTest() {

        assertEquals(url, hotCountry.getUrl());
    }

    @Test
    public void getArtistSongTest() {

        ArrayList<Charts> expected = hotCountry.getArtistSongs();
        assertNotNull(expected);
    }

    @Test
    public void getArtistNameTest() {

        String name = hotCountry.getArtistName();
        assertEquals("Mason Ramsey", name);
    }

}
