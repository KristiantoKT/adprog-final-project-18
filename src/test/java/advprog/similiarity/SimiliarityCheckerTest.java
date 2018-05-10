package advprog.similiarity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SimiliarityCheckerTest {

    private String text1;
    private String text2;
    private String result;

    @Test
    public void testCheckerWithSameText() {
        text1 = "Hello everybody how are you today boi!";
        text2 = "Hello everybody how are you today boi!";

        assertEquals(100.0, SimiliartyChecker.checkSimiliarity(text1,text2),0.001);

    }

    @Test
    public void testCheckerWithDifferentText() {
        text1 = "Hello everybody how are you today boi!!";
        text2 = "Ich guten morgen italy";

        assertEquals(0.0, SimiliartyChecker.checkSimiliarity(text1,text2),0.01);
    }

    @Test
    public void testUrlValidity() {
        String urlValid = "https://www.google.com/";
        String urlInvalid = "not a website";

        assertTrue(SimiliartyChecker.checkUrlValidity(urlValid,urlValid));
        assertFalse(SimiliartyChecker.checkUrlValidity(urlInvalid,urlInvalid));
    }
}
