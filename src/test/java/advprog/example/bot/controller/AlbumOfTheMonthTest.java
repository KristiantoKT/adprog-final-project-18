package advprog.example.bot.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class AlbumOfTheMonthTest {
    String url;
    ArrayList<Soundtrack> albumOfTheMonthList;
    AlbumOfTheMonth albumOfTheMonth;

    @Before
    public void setUp() {
        url = "https://vgmdb.net/db/calendar.php?year=2018&month=5";
        albumOfTheMonthList = new ArrayList<>();
        albumOfTheMonth = new AlbumOfTheMonth(url);
    }

    @Test
    public void setSoundtracksOfTheMonthListTest() {
        albumOfTheMonth.setSoundtracksOfTheMonthList(url);
        albumOfTheMonthList = albumOfTheMonth.getSoundtracksOfTheMonth();

        assertFalse(albumOfTheMonthList.isEmpty());
    }

    @Test
    public void currencyConverterTest() throws IOException {
        String from = "USD";
        String price = "8.99";
        String result = albumOfTheMonth.currencyConverter(price, from);

        assertEquals(result, "127388.3 IDR");

    }
}
