package advprog.favoritehotcountry.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChartsTest {
    Charts chart;

    @BeforeEach
    void setUp() {
        chart = new Charts("Heaven", "Kane Brown", 2);
    }

    @Test
    void getPosition() {

        assertEquals(2, chart.getPosition());
    }

    @Test
    void setPosition() {
        chart.setPosition(10);
        assertEquals(10, chart.getPosition());
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


}
