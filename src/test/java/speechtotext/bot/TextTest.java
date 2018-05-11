package speechtotext.bot;

import advprog.speechtotext.bot.Text;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextTest {

    private Text text;

    @Before
    public void setUp() {
        text = new Text("Gang gang");
    }

    @Test
    public void getTest() {
        assertTrue("Gang gang".equals(text.getSpeechText()));
    }

    public void setTest() {
        text.setSpeechText("Gung gung");
        assertTrue("Gung gung".equals(text.getSpeechText()));
    }
}
