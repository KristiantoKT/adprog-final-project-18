package advprog.popularhotcountry.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChartsTest {
    Charts chart;

    @BeforeEach
    void setUp() {

        chart = new Charts("Heaven", "Kane Brown");
    }

    @Test
    void getSong() {

        assertEquals("Heaven", chart.getSong());
    }

    @Test
    void setSong() {
        chart.setSong("Famous");
        assertEquals("Famous", chart.getSong());
    }

    @Test
    void getArtist() {

        assertEquals("Kane Brown", chart.getArtist());
    }

    @Test
    void setArtist() {
        chart.setArtist("Luke Combs");
        assertEquals("Luke Combs", chart.getArtist());
    }

    @Test
    void toStringTest() {
        assertEquals("Kane Brown - Heaven", chart.toString());
    }
}
