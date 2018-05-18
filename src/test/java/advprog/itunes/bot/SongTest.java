package advprog.itunes.bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SongTest {

    Song song;

    @BeforeEach
    void setUp() {
        song = new Song("Upside Down", "Jack Johnson", "http://a1099.itunes.apple.com/r10/Music/f9/54/43/mzi.gqvqlvcq.aac.p.m4p");
    }

    @Test
    void getTitle() {
        assertEquals("Upside Down", song.getTitle());
    }

    @Test
    void setTitle() {
        song.setTitle("Tired");
        assertEquals("Tired", song.getTitle());
    }

    @Test
    void getArtist() {
        assertEquals("Jack Johnson", song.getArtist());
    }

    @Test
    void setArtist() {
        song.setArtist("Nixi");
        assertEquals("Nixi", song.getArtist());
    }

    @Test
    void getPreviewUrl() {
        assertEquals("http://a1099.itunes.apple.com/r10/Music/f9/54/43/mzi.gqvqlvcq.aac.p.m4p", song.getPreviewUrl());
    }

    @Test
    void setPreviewUrl() {
        song.setPreviewUrl("nah.com");
        assertEquals("nah.com", song.getPreviewUrl());
    }
}
