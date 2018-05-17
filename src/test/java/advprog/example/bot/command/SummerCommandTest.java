package advprog.example.bot.command;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

public class SummerCommandTest {
    private String testDate = "summer-2018";
    private SeasonCommand command = new SummerCommand();
    private TextMessage reply;

    private String sampleTitle = "Attack on Titan";
    private String sampleSynopsis = "Eren Jaeger";


    @Test
    public void testInstanceOf() {
        Assert.assertTrue(command instanceof SummerCommand);
    }

    @Test
    public void testValidOutput() throws IOException {
        reply = command.execute(testDate);
        reply.getText().contains(sampleTitle);
        reply.getText().contains(sampleSynopsis);
    }

}
