package advprog.example.bot.livechart.anime;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class ScrappingTest {
    Scrapping scrapping;
    String stringContains = "FLCL Progressive";

    String testUrl = "https://www.livechart.me/summer-2018/tv";

    String scrapOut;

    public ScrappingTest() throws IOException {
        scrapping = new Scrapping();
    }

    @Test
    public void constructorTest() {
        Assert.assertNotNull(scrapping);
    }

    @Test
    public void sampleTest() throws IOException {
        scrapOut = scrapping.scrap(testUrl);
        scrapOut.contains(stringContains);
    }
}
