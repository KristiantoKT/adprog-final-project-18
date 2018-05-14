package speechtotext.bot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import advprog.speechtotext.bot.FetchStuff;
import java.io.File;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class FetchStuffTest {

    private File soundFile;

    @Before
    public void setUp() {
        soundFile = new File("soundfile/soundtest.wav");
    }

    @Test
    public void tokenIsString() {
        String namaClass = FetchStuff.getTokenFromApi().getClass().getSimpleName();
        assertTrue(namaClass.equals("String"));
    }

    @Test
    public void textIsTextType() throws IOException {
        String namaClass = FetchStuff.getTextFromSpeech(soundFile).getClass().getSimpleName();
        assertTrue(namaClass.equals("Text"));
    }
}
