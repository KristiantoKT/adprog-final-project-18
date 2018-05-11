package advprog.example.bot.tropical;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SongTest {
    Song song;

    @BeforeEach
    void setUp() {
        song = new Song("The Final Countdown", "Europe");
    }

    @Test
    void getName() {
        assertEquals("The Final Countdown", song.getName());
    }

    @Test
    void setName() {
        song.setName("Carrie");
        assertEquals("Carrie", song.getName());
    }

    @Test
    void getArtist() {
        assertEquals("Europe", song.getArtist());
    }

    @Test
    void setArtist() {
        song.setArtist("Various Artist");
        assertEquals("Various Artist", song.getArtist());
    }
}