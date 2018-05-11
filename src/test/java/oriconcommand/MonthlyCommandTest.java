package oriconcommand;

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
            + "\n(1) シンクロニシティ - 乃木坂46 - 2018-04-25\n"
            + "(2) 早送りカレンダー - HKT48 - 2018-05-02\n"
            + "(3) Ask Yourself - KAT-TUN - 2018-04-18\n"
            + "(4) 春はどこから来るのか? - NGT48 - 2018-04-11\n"
            + "(5) 君のAchoo! - ラストアイドル(シュークリームロケッツ) - 2018-04-18\n"
            + "(6) SEXY SEXY/泣いていいよ/Vivid Midnight - Juice=Juice - 2018-04-18\n"
            + "(7) ガラスを割れ! - 欅坂46 - 2018-03-07\n"
            + "(8) ONE TIMES ONE - コブクロ - 2018-04-11\n"
            + "(9) ODD FUTURE - UVERworld - 2018-05-02\n"
            + "(10) Shanana ここにおいで - B2takes! - 2018-04-11";

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
