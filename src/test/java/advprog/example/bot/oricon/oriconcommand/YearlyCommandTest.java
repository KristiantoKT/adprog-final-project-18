package advprog.example.bot.oricon.oriconcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Test;

public class YearlyCommandTest {
    private String testDate = "2017";
    private String failDate = "2019";
    private RankCommand command = new YearlyRankCommand();
    private TextMessage reply;

    private String sampleYearlyOutput = "Top 10!"
            + "\n(1) 願いごとの持ち腐れ - AKB48 - 2017-05-31\n"
            + "(2) #好きなんだ - AKB48 - 2017-08-30\n"
            + "(3) 11月のアンクレット - AKB48 - 2017-11-22\n"
            + "(4) シュートサイン - AKB48 - 2017-03-15\n"
            + "(5) 逃げ水 - 乃木坂46 - 2017-08-09\n"
            + "(6) インフルエンサー - 乃木坂46 - 2017-03-22\n"
            + "(7) いつかできるから今日できる - 乃木坂46 - 2017-10-11\n"
            + "(8) 不協和音 - 欅坂46 - 2017-04-05\n"
            + "(9) 風に吹かれても - 欅坂46 - 2017-10-25\n"
            + "(10) Doors 〜勇気の軌跡〜 - 嵐 - 2017-11-08";

    @Test
    public void testInstanceOf() {
        assertTrue(command instanceof YearlyRankCommand);
    }

    @Test
    public void testValidOutput() throws IOException {
        reply = command.execute(testDate);

        assertEquals(sampleYearlyOutput,reply.getText());
    }
}
