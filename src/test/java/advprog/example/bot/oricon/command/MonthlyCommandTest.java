package advprog.example.bot.oricon.command;

import java.io.IOException;

import com.linecorp.bot.model.message.TextMessage;
import org.junit.Assert;
import org.junit.Test;

public class MonthlyCommandTest {
    private String testDate = "2018-04";
    private String failDate = "2019-04";
    private Command command = new MonthlyCommand();
    private TextMessage reply;

    private String sampleMonthlyOutput = "Top 10\n"
            + "(1) 進撃の巨人 - (画)諫山創\n"
            + "(2) キングダム - (画)原泰久\n"
            + "(3) 僕のヒーローアカデミア - (画)堀越耕平\n"
            + "(4) 七つの大罪 - (画)鈴木央\n"
            + "(5) よつばと! - (画)あずまきよひこ\n"
            + "(6) ゴールデンカムイ - (画)野田サトル\n"
            + "(7) ダンジョン飯 - (画)九井諒子\n"
            + "(8) ハイキュー!! - (画)古舘春一\n"
            + "(9) ワンパンマン - (原作)ONE/(画)村田雄介\n"
            + "(10) 約束のネバーランド - (原作)白井カイウ/(画)出水ぽすか";


    @Test
    public void testInstanceOf() {
        Assert.assertTrue(command instanceof MonthlyCommand);
    }

    @Test
    public void testValidOutput() throws IOException {
        reply = command.execute(testDate);
        Assert.assertEquals(sampleMonthlyOutput,reply.getText());
    }

}
