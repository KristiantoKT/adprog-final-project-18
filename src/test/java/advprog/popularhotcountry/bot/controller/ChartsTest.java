package advprog.popularhotcountry.bot.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChartsTest {
    Charts chart;

    void setUp() {
        cahrt = new Charts("Heaven", "Kane Brown");
    }

    @Test
    void getSong() {
        assertEquals("Heaven", chart.getName());
    }

    @Test
    void setSong() {
        song.setName("Famous");
        assertEquals("Famous", chart.getName());
    }

    @Test
    void getArtist() {
        assertEquals("Kane Brown", chart.getArtist());
    }

    @Test
    void setArtist() {
        song.setArtist("Luke Combs");
        assertEquals("Luke Combs", chart.getArtist());
    }

    @Test
    void toStringTest() {
        assertEquals("Kane Brown - Heaven", song.toString());
    }
}
