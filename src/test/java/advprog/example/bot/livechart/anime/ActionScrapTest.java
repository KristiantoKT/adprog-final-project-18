package advprog.example.bot.livechart.anime;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ActionScrapTest {
    ActionScrap scrapping;
    String stringContains = "High School DxD Hero";

    String testUrl = "https://www.livechart.me/spring-2018/tv";

    String scrapOut;

    public ActionScrapTest() throws IOException {
        scrapping = new ActionScrap();
    }

    @Test
    public void constructorTest() {
        Assert.assertNotNull(scrapping);
    }

    @Test
    public void sampleTest() throws IOException {
        scrapOut = scrapping.actionScrap(testUrl);
        scrapOut.contains(stringContains);
    }
}
