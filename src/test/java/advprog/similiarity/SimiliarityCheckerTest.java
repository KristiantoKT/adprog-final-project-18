package advprog.similiarity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SimiliarityCheckerTest {

    private String param1;
    private String param2;

    @Test
    public void testCheckerWithSameText() {
        param1 = "Hello everybody how are you today boy!";
        param2 = "Hello everybody how are you today boy!";

        assertEquals(100.0, SimiliartyChecker.checkSimiliarity(param1, param2),0.001);

    }

    @Test
    public void testCheckerWithDifferentText() {
        param1 = "Hello everybody how are you today boi!!";
        param2 = "Burger is expensive eat coca cola instead";

        assertEquals(0.0, SimiliartyChecker.checkSimiliarity(param1, param2),0.01);
    }

    @Test
    public void testCheckerWithUrlInput() {
        param1 =  "https://en.wikipedia.org/wiki/Wikipedia";
        param2 = "https://en.wikipedia.org/wiki/Kenn_Whitaker";

        assertEquals(17.22,SimiliartyChecker.checkSimiliarity(param1,param2), 0.01);
    }

    @Test
    public void testCheckerWithUrlInputWithInvalidLanguage() {
        param1 =  "https://de.wikipedia.org/wiki/Wikipedia:Hauptseite";
        param2 = "https://de.wikipedia.org/wiki/Kenn_Whitaker";

        assertEquals(-1,SimiliartyChecker.checkSimiliarity(param1,param2), 0.01);
    }

    @Test
    public void testUrlValidity() {
        String urlValid = "https://www.google.com/";
        String urlInvalid = "not a website";

        assertTrue(SimiliartyChecker.checkUrlValidity(urlValid,urlValid));
        assertFalse(SimiliartyChecker.checkUrlValidity(urlInvalid,urlInvalid));
    }
}
