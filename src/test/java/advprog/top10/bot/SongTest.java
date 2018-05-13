package advprog.top10.bot;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class SongTest {
    Song song;

    @Before
    public void setUp() {
        song = new Song("Im tired pls send help", "Nixi");
    }

    @Test
    public void returnArtistTest() {
        String artist = song.getArtist();
        assertEquals(artist, "Nixi");
    }

    @Test
    public void returnSong() {
        String songs = song.getTitle();
        assertEquals(songs, "Im tired pls send help");
    }

    @Test
    public void setSongTest() {
        song.setTitle("tired 24/7");
        String songNew = song.getTitle();
        assertEquals(songNew, "tired 24/7");
    }

    @Test
    public void setNameTest() {
        song.setArtist("Dead");
        String artistNew = song.getArtist();
        assertEquals(artistNew, "Dead");
    }
}
