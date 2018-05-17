package advprog.example.bot.command;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;


public class WinterCommandTest {
    private String testDate = "winter-2018";
    private SeasonCommand command = new WinterCommand();
    private TextMessage reply;

    private String sampleTitle = "Overlord II";
    private String sampleSynopsis = "In a world where magic is everything";


    @Test
    public void testInstanceOf() {
        Assert.assertTrue(command instanceof WinterCommand);
    }

    @Test
    public void testValidOutput() throws IOException {
        reply = command.execute(testDate);
        reply.getText().contains(sampleTitle);
        reply.getText().contains(sampleSynopsis);
    }
}
