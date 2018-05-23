package advprog.anison.bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class SongSearchTest {

    @Test
    public void findItunesIdTest() throws Exception {
        String song = "Soldier Game";

        assertEquals(587762424,SongSearch.findItunesId(song));
    }

    @Test
    public void findNonexistentSongTest() throws Exception {
        String song = "Yes Bang Dream";

        assertEquals(-1,SongSearch.findItunesId(song));
    }

    @Test
    public void findSongWithoutItunesIdTest() throws Exception {
        String song = "Daydream Warrior";

        assertEquals(-2,SongSearch.findItunesId(song));
    }

    @Test
    public void findImageUrlTest() throws Exception {
        String song = "Soldier Game";

        assertEquals("//i.schoolido.lu/songs/soldier_game.jpg",SongSearch.findImageUrl(song));
    }

    @Test
    public void findSongTrueNameTest() throws Exception {
        String song = "Soldier Game";

        assertEquals("soldier game",SongSearch.findSongTrueName(song));
    }
}
