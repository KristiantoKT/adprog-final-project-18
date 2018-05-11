package advprog.popularhotcountry.bot.controller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class HotCountryTest {
    HotCountry hotCountry;
    String url = "https://www.billboard.com/charts/country-songs";

    @Before
    public void SetUp() {
        hotCountry = new HotCountry(url);
    }

    @Test
    public void urlNotValidTest() {
        new HotCountry("https://haduh.ac.id/");
    }

    @Test
    public void listTopTenTest() {
        String expected = hotCountry.listTopTen();
        assertTrue(expected.contains("Famous"));
    }

    @Test
    public void getUrlTest() {
        assertEquals(url, hotCountry.getUrl());
    }

    @Test
    public void getHotCountryTest() {
        ArrayList<Charts> list = hotCountry.getHotCountryTopTen();
        assertEquals(10, list.size());
    }
}
