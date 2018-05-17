package advprog.top20album;


import static org.junit.jupiter.api.Assertions.assertEquals;

import advprog.top20album.bot.Album;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AlbumTest {
    Album album;

    @BeforeEach
    void setUp() {
        album = new Album(1, "CHRONO CROSS ORIGINAL SOUNDTRACK", 4.73, 394857);
    }

    @Test
    void getName() {
        assertEquals("CHRONO CROSS ORIGINAL SOUNDTRACK", album.getName());
    }

    @Test
    void setName() {
        album.setName("bloop");
        assertEquals("bloop", album.getName());
    }

    @Test
    void getPosition() {
        assertEquals(1, album.getPosition());
    }

    @Test
    void setPosition() {
        album.setPosition(2);
        assertEquals(2, album.getPosition());
    }

    @Test
    void getRating() {
        assertEquals(4.73, album.getRating());
    }

    @Test
    void setRating() {
        album.setRating(5.00);
        assertEquals(5.00, album.getRating());
    }

    @Test
    void getPrice() {
        assertEquals(394857, album.getPrice());
    }

    @Test
    void setPrice() {
        album.setPrice(1000);
        assertEquals(1000, album.getPrice());
    }

    @Test
    void toStringTest() {
        assertEquals("1 - CHRONO CROSS ORIGINAL SOUNDTRACK - 4.73 (394857 IDR)", album.toString());
    }

}
