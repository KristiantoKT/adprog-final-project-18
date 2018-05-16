package advprog.example.bot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SongTest {
    Song song;

    @BeforeEach
    void setUp() {
        song = new Song("Cherry Bomb", "NCT 127", 1);
    }

    @Test
    void getName() {
        assertEquals("Cherry Bomb", song.getName());
    }

    @Test
    void getArtist() {
        assertEquals("NCT 127", song.getArtist());
    }

    @Test
    void getPosition() {
        assertEquals(1, song.getPosition());
    }

    @Test
    void setName() {
        song.setName("Scentist");
        assertEquals("Scentist", song.getName());
    }

    @Test
    void setArtist() {
        song.setArtist("VIXX");
        assertEquals("VIXX", song.getArtist());
    }

    @Test
    void setPosition() {
        song.setPosition(2);
        assertEquals(2, song.getPosition());
    }

    @Test
    void toStringTest() {
        assertEquals("NCT 127 - Cherry Bomb", song.toString());
    }
}