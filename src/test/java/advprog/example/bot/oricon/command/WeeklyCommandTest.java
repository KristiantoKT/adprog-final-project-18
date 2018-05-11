package advprog.example.bot.oricon.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Test;

public class MonthlyCommandTest {
    private String testDate = "2018-05-07";
    private String failDate = "2019-05-07";
    private RankCommand command = new WeeklyRankCommand();
    private TextMessage reply;

    private String sampleWeeklyOutput = "Top 10\n"
            + "(1) \n"
            + "(2) \n"
            + "(3) \n"
            + "(4) \n"
            + "(5) \n"
            + "(6) \n"
            + "(7) \n"
            + "(8) \n"
            + "(9) \n"
            + "(10) ";


    @Test
    public void testInstanceOf() {
        assertTrue(command instanceof WeeklyRankCommand);
    }

    @Test
    public void testValidOutput() throws IOException {
        reply = command.execute(testDate);

        assertEquals(sampleWeeklyOutput,reply.getText());
    }

}
