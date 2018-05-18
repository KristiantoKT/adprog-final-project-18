package advprog.top20album;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import advprog.top20album.bot.Album;
import advprog.top20album.bot.TopAlbum;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class TopAlbumTest {
    TopAlbum topAlbum;
    String url = "https://vgmdb.net/db/statistics.php?do=top_rated";

    @Before
    public void setUp() {
        topAlbum = new TopAlbum(url);
    }

    @Test
    public void urlErrorTest() {
        new TopAlbum("https://hehe.id/");
    }

    @Test
    public void getUrlTest() {
        assertEquals(url, topAlbum.getUrl());
    }

    @Test
    public void getTop20AlbumsTest() {
        String output = topAlbum.printUrl();
        assertTrue(output.contains("https://vgmdb.net/album/4"));
    }

    @Test
    public void getListOfUrls() {
        List<String> list = topAlbum.getListOfUrl();
        assertEquals(20, list.size());
    }

    @Test
    public void getListOfAlbums() {
        List<Album> list = topAlbum.getListOfAlbums();
        assertEquals(20, list.size());
    }




}
