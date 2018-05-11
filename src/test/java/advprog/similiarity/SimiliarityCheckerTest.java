package advprog.similiarity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SimiliarityCheckerTest {

    private String param;

    @Test
    public void testCheckerWithSameText() {
        param = "'Hello everybody how are you today boy!' 'Hello everybody how are you today boy!'";

        assertEquals(100.0, SimiliartyChecker.checkSimiliarity(param),0.001);

    }

    @Test
    public void testCheckerWithDifferentAndInvalidText() {
        param = "'Hello everybody how are you today boy!' 'Burger is expensive eat coca cola instead'";

        assertEquals(0.0, SimiliartyChecker.checkSimiliarity(param),0.01);


        param = "Hello everybody how are you today boy!' 'Burger is expensive eat coca cola instead";

        assertEquals(-1.0, SimiliartyChecker.checkSimiliarity(param),0.01);
    }

    @Test
    public void testCheckerWithUrlInput() {
        param =  "https://en.wikipedia.org/wiki/Wikipedia https://en.wikipedia.org/wiki/Kenn_Whitaker";

        assertEquals(17.22,SimiliartyChecker.checkSimiliarity(param), 0.01);
    }

    @Test
    public void testCheckerWithUrlInputWithInvalidLanguage() {
        param =  "https://de.wikipedia.org/wiki/Wikipedia:Hauptseite https://de.wikipedia.org/wiki/Kenn_Whitaker";

        assertEquals(-1,SimiliartyChecker.checkSimiliarity(param), 0.01);
    }

    @Test
    public void testUrlValidity() {
        String urlValid = "https://www.google.com/";
        String urlInvalid = "not a website";

        assertTrue(SimiliartyChecker.checkUrlValidity(urlValid,urlValid));
        assertFalse(SimiliartyChecker.checkUrlValidity(urlInvalid,urlInvalid));
    }

    @Test
    public void testInvalidInputWhenParsing() {
        param = "asdasdasda' 'asdasdasd";

        String[] parsed = SimiliartyChecker.parseInput(param);
        assertEquals("error", parsed[0]);

        param = "https://nasdhabsdashdbj.gg https://www.www.ez";

        parsed = SimiliartyChecker.parseInput(param);
        assertEquals("error", parsed[0]);

    }

    @Test
    public void testValidInputWhenParsing() {
        param = "'Hello everybody how are you today boy!' 'Burger is expensive eat coca cola instead'";

        String[] parsed = SimiliartyChecker.parseInput(param);
        assertEquals("text", parsed[0]);
        assertEquals("Hello everybody how are you today boy!", parsed[1]);
        assertEquals("Burger is expensive eat coca cola instead", parsed[2]);

        param = "https://de.wikipedia.org/wiki/Wikipedia:Hauptseite https://de.wikipedia.org/wiki/Kenn_Whitaker";

        parsed = SimiliartyChecker.parseInput(param);
        assertEquals("url", parsed[0]);
        assertEquals("https://de.wikipedia.org/wiki/Wikipedia:Hauptseite", parsed[1]);
        assertEquals("https://de.wikipedia.org/wiki/Kenn_Whitaker", parsed[2]);
    }
}
