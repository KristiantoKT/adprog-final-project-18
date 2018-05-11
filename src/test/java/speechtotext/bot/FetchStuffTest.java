package speechtotext.bot;

import advprog.speechtotext.bot.FetchStuff;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FetchStuffTest {
    @Test
    public void tokenIsString() {
        String namaClass = FetchStuff.getTokenFromAPI().getClass().getSimpleName();
        assertTrue(namaClass.equals("String"));
    }

    @Test
    public void textIsTextType() throws IOException {
        String namaClass = FetchStuff.getTextFromSpeech().getClass().getSimpleName();
        assertTrue(namaClass.equals("Text"));
    }
}
