package advprog.example.bot.tropical;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BillboardTropicalTest {
    BillboardTropical billboardTropical;
    String url = "https://www.billboard.com/charts/tropical-songs";

    @Before
    public void setUp() {
        billboardTropical = new BillboardTropical(url);
    }

    @Test
    public void printTopTenList() {
        String expectedOutput = billboardTropical.printTopTenList();
        assertTrue(expectedOutput.contains("Raymix"));
    }
}
