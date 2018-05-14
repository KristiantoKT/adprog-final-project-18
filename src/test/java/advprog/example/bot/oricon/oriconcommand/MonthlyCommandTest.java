package advprog.example.bot.oricon.oriconcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Test;

public class MonthlyCommandTest {
    private String testDate = "2018-04";
    private String failDate = "2019-04";
    private RankCommand command = new MonthlyRankCommand();
    private TextMessage reply;

    private String sampleMonthlyOutput = "Top 10!"
            + "\n(1) シンクロニシティ - 乃木坂46 - 2018-04-25 - Not Available\n"
            + "(2) 早送りカレンダー - HKT48 - 2018-05-02 - Not Available\n"
            + "(3) Ask Yourself - KAT-TUN - 2018-04-18 - Not Available\n"
            + "(4) 春はどこから来るのか? - NGT48 - 2018-04-11 - Not Available\n"
            + "(5) 君のAchoo! - ラストアイドル(シュークリームロケッツ) - 2018-04-18 - 58,198\n"
            + "(6) SEXY SEXY/泣いていいよ/Vivid Midnight - Juice=Juice - 2018-04-18 - 54,728\n"
            + "(7) ガラスを割れ! - 欅坂46 - 2018-03-07 - 54,131\n"
            + "(8) ONE TIMES ONE - コブクロ - 2018-04-11 - 39,395\n"
            + "(9) ODD FUTURE - UVERworld - 2018-05-02 - 37,347\n"
            + "(10) Shanana ここにおいで - B2takes! - 2018-04-11 - 36,455";

    @Test
    public void testInstanceOf() {
        assertTrue(command instanceof MonthlyRankCommand);
    }

    @Test
    public void testValidOutput() throws IOException {
        reply = command.execute(testDate);

        assertEquals(sampleMonthlyOutput,reply.getText());
    }

}
