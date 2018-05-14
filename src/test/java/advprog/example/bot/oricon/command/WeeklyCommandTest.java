package advprog.example.bot.oricon.command;

import com.linecorp.bot.model.message.TextMessage;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class WeeklyCommandTest {
    private String testDate = "2018-05-07";
    private String failDate = "2019-05-07";
    private Command command = new WeeklyCommand();
    private TextMessage reply;

    private String sampleWeeklyOutput = "Top 10\n"
            + "(1) キングダム - (画)原泰久\n"
            + "(2) よつばと! - (画)あずまきよひこ\n"
            + "(3) 宇宙兄弟 - (画)小山宙哉\n"
            + "(4) 思い、思われ、ふり、ふられ - (画)咲坂伊緒\n"
            + "(5) 七つの大罪 - (画)鈴木央\n"
            + "(6) GIANT KILLING - (画)ツジトモ/(原案・取材協力)綱本将也\n"
            + "(7) 僕のヒーローアカデミア - (画)堀越耕平\n"
            + "(8) ゴールデンカムイ - (画)野田サトル\n"
            + "(9) 進撃の巨人 - (画)諫山創\n"
            + "(10) 花のち晴れ〜花男 Next Season〜 - (画)神尾葉子";


    @Test
    public void testInstanceOf() {
        Assert.assertTrue(command instanceof WeeklyCommand);
    }

    @Test
    public void testValidOutput() throws IOException {
        reply = command.execute(testDate);

        Assert.assertEquals(sampleWeeklyOutput,reply.getText());
    }

}
