package advprog.example.bot.controller;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;

import org.junit.Before;



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

    public void setSoundtracksOfTheMonthListTest() {
        albumOfTheMonth.setSoundtracksOfTheMonthList(url);
        albumOfTheMonthList = albumOfTheMonth.getSoundtracksOfTheMonth();

        assertFalse(albumOfTheMonthList.isEmpty());
    }
}
