package advprog.favartist.bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class SongTest {
    Song song;

    @BeforeEach
    void setUp() {
        song = new Song("beerbongs & bentleys", "Post Malone", 1);
    }

    @Test
    void getTitle() {
        assertEquals("beerbongs & bentleys", song.getTitle());
    }

    @Test
    void setTitle() {
        song.setTitle("bloop");
        assertEquals("bloop", song.getTitle());
    }

    @Test
    void getArtist() {
        assertEquals("Post Malone", song.getArtist());
    }

    @Test
    void setArtist() {
        song.setArtist("yeet");
        assertEquals("yeet", song.getArtist());
    }

    @Test
    void getPosition() {
        assertEquals(1, song.getPosition());
    }

    @Test
    void setPosition() {
        song.setPosition(2);
        assertEquals(2, song.getPosition());
    }

    @Test
    void toStringTest() {
        assertEquals("Post Malone - beerbongs & bentleys", song.toString());
    }
}
