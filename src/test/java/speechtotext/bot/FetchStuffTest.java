package speechtotext.bot;

import advprog.speechtotext.bot.FetchStuff;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FetchStuffTest {

    private File soundFile;
    @Before
    public void setUp() {
        soundFile = new File("soundfile/soundtest.wav");
    }

    @Test
    public void tokenIsString() {
        String namaClass = FetchStuff.getTokenFromAPI().getClass().getSimpleName();
        assertTrue(namaClass.equals("String"));
    }

    @Test
    public void textIsTextType() throws IOException {
        String namaClass = FetchStuff.getTextFromSpeech(soundFile).getClass().getSimpleName();
        assertTrue(namaClass.equals("Text"));
    }
}
