package advprog.example.bot.tropical;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
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
    public void urlErrorTest() {
        new BillboardTropical("https://haha.id/");
    }

    @Test
    public void printTopTenList() {
        String expectedOutput = billboardTropical.printTopTenList();
        assertTrue(expectedOutput.contains("Raymix"));
    }

    @Test
    public void getBillboardUrl() {
        assertEquals(url, billboardTropical.getBillboardUrl());
    }

    @Test
    public void getTopTenTropicalList() {
        List<Song> list = billboardTropical.getTopTenTropicalList();
        assertEquals(10, list.size());
    }
}
