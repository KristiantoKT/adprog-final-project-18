package advprog.example.bot.oricon.comic;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class ComicTest {
    Comic comic;

    @BeforeEach
    void setUp() {
        comic = new Comic("魔法使いの嫁 9", "ヤマザキコレ");
    }

    @Test
    void getTitle() {
        Assertions.assertEquals("魔法使いの嫁 9", comic.getTitle());
    }

    @Test
    void setTitle() {
        comic.setTitle("Dave");
        Assertions.assertEquals("Dave", comic.getTitle());
    }

    @Test
    void getAuthor() {
        Assertions.assertEquals("ヤマザキコレ", comic.getAuthor());
    }

    @Test
    void setAuthor() {
        comic.setAuthor("ヤマザキコレ");
        Assertions.assertEquals("ヤマザキコレ", comic.getAuthor());
    }
}
