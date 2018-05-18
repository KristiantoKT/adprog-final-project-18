package advprog.example.bot.command;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;


public class SpringCommandTest {
    private String testDate = "spring-2018";
    private SeasonCommand command = new SpringCommand();
    private TextMessage reply;

    private String sampleTitle = "Boku no Hero Academia 3rd Season";
    private String sampleSynopsis = "Izuku";


    @Test
    public void testInstanceOf() {
        Assert.assertTrue(command instanceof SpringCommand);
    }

    @Test
    public void testValidOutput() throws IOException {
        reply = command.execute(testDate, "Action");
        reply.getText().contains(sampleTitle);
        reply.getText().contains(sampleSynopsis);
    }

}
