package speechtotext.bot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import advprog.speechtotext.bot.FetchStuff;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FetchStuffTest {

    private File soundFile;
    private byte[] bytes;

    @Before
    public void setUp() throws IOException {
        soundFile = new File("soundfile/soundtest.wav");
    }

    @Test
    public void tokenIsString() {
        String namaClass = FetchStuff.getTokenFromApi().getClass().getSimpleName();
        assertTrue(namaClass.equals("String"));
    }

    @Test
    public void textIsTextType() throws IOException {
        FileInputStream fileStream = new FileInputStream(soundFile);
        byte[] bytes = new byte[(int) soundFile.length()];
        fileStream.read(bytes);
        fileStream.close();
        String namaClass = FetchStuff.getTextFromSpeech(bytes).getClass().getSimpleName();
        assertTrue(namaClass.equals("Text"));
    }

    @Test
    public void getTextFromSpeechReturnsProperValue() throws IOException {
        FileInputStream fileStream = new FileInputStream(soundFile);
        byte[] bytes = new byte[(int) soundFile.length()];
        fileStream.read(bytes);
        fileStream.close();
        assertTrue(FetchStuff.getTextFromSpeech(bytes).getSpeechText()
                .equals("Every word and phrase he speaks is true."));
    }
}
