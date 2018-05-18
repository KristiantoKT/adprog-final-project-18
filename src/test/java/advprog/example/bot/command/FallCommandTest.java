package advprog.example.bot.command;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;


public class FallCommandTest {
    private String testDate = "fall-2018";
    private SeasonCommand command = new FallCommand();
    private TextMessage reply;

    private String sampleTitle = "Goblin Slayer";
    private String sampleSynopsis = "A young priestess has "
           +  "formed her first adventuring party";


    @Test
    public void testInstanceOf() {
        Assert.assertTrue(command instanceof FallCommand);
    }

    @Test
    public void testValidOutput() throws IOException {
        reply = command.execute(testDate, "Action");
        reply.getText().contains(sampleTitle);
        reply.getText().contains(sampleSynopsis);
    }

}
