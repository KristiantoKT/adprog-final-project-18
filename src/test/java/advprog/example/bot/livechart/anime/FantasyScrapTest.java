package advprog.example.bot.livechart.anime;

import java.io.IOException;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class FantasyScrapTest {
    FantasyScrap scrapping;
    String stringContains = "Persona 5 the Animation";

    String testUrl = "https://www.livechart.me/spring-2018/tv";

    String scrapOut;

    public FantasyScrapTest() {
        scrapping = new FantasyScrap();
    }

    @Test
    public void constructorTest() {
        Assert.assertNotNull(scrapping);
    }

    @Test
    public void sampleTest() throws IOException {
        scrapOut = scrapping.fantasyScrap(testUrl);
        scrapOut.contains(stringContains);
    }
}
