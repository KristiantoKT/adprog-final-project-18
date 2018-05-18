package advprog.example.bot.livechart.anime;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class ComedyScrapTest {
    ComedyScrap scrapping;
    String stringContains = "Hinamatsuri";

    String testUrl = "https://www.livechart.me/spring-2018/tv";

    String scrapOut;

    public ComedyScrapTest() throws IOException {
        scrapping = new ComedyScrap();
    }

    @Test
    public void constructorTest() {
        Assert.assertNotNull(scrapping);
    }

    @Test
    public void sampleTest() throws IOException {
        scrapOut = scrapping.comedyScrap(testUrl);
        scrapOut.contains(stringContains);
    }
}
