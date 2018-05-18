package advprog.itunes.bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ItunesTest {
    Itunes itunes;
    String artist;

    @BeforeEach
    void setUp() {
        itunes = new Itunes(artist);
    }

    @Test
    void getPreviewUrl() { }


}
