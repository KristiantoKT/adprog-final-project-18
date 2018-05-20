package advprog.example.bot.command;

import static org.junit.Assert.assertEquals;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class SeasonCommandTest {
    private String testDate = "https://www.livechart.me/spring-2018/tv";
    private SeasonCommand command = new SeasonCommand();
    private TextMessage reply;

    private String sampleTitle = "Boku no Hero Academia 3rd Season";
    private String sampleSynopsis = "Izuku";

    @Before
    public void setUp() {
        command.setYear("2018");
        command.setSeason("spring");
        command.setGenre("Action");
        command.setUrl();
    }

    @Test
    public void testInstanceOf() {
        Assert.assertTrue(command instanceof SeasonCommand);
    }

    @Test
    public void testValidUrl() {
        assertEquals(command.getUrl(), testDate);
    }

    @Test
    public void testGetYear() {

        assertEquals("2018", command.getYear());
    }

    @Test
    public void testGetSeason() {

        assertEquals("spring", command.getSeason());
    }

    @Test
    public void testGetGenre() {

        assertEquals("Action", command.getGenre());
    }
}
