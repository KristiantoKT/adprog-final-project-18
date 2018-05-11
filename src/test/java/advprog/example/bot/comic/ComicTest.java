package advprog.example.bot.comic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComicTest {
    Comic comic;

    @BeforeEach
    void setUp() {
        comic = new Comic("魔法使いの嫁 9", "ヤマザキコレ");
    }

    @Test
    void getTitle() {
        assertEquals("魔法使いの嫁 9", comic.getTitle());
    }

    @Test
    void setTitle() {
        comic.setTitle("Dave");
        assertEquals("Dave", comic.getTitle());
    }

    @Test
    void getAuthor() {
        assertEquals("ヤマザキコレ", comic.getAuthor());
    }

    @Test
    void setAuthor() {
        comic.setArtist("ヤマザキコレ");
        assertEquals("ヤマザキコレ", comic.getAuthor());
    }






}
