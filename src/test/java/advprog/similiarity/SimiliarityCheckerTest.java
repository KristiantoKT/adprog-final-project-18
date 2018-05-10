package advprog.similiarity;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimiliarityCheckerTest {

    private String text1;
    private String text2;
    private String result;

    @Test
    public void testCheckerWithSameText() {
        text1 = "Halo apa kabar kawan semua!";
        text2 = "Halo apa kabar kawan semua!";

        assertEquals(100.0, SimiliartyChecker.checkSimiliarity(text1,text2),0.001);

    }

    @Test
    public void testCheckerWithDifferentText() {
        text1 = "Halo apa kabar semuanya!";
        text2 = "wadidaw dibalik jadi wadidaw";

        assertEquals(0.0, SimiliartyChecker.checkSimiliarity(text1,text2),0.01);
    }

    @Test
    public void testUrlValidity() {
        String urlValid = "https://www.google.com/";
        String urlInvalid = "not a website";

        assertTrue(SimiliartyChecker.checkUrlValidity(urlValid));
        assertFalse(SimiliartyChecker.checkUrlValidity(urlInvalid));
    }
}
