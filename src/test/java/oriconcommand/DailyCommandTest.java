package oriconcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

import org.junit.Test;

public class DailyCommandTest {

    private String testDate = "2018-05-09";
    private String failDate = "2019-05-08";
    private RankCommand command = new DailyRankCommand();
    private TextMessage reply;

    private String sampleDailyOutput = "Top 10!"
            + "\n(1) 進化理論 - BOYS AND MEN - 2018-05-09\n"
            + "(2) シンクロニシティ - 乃木坂46 - 2018-04-25\n"
            + "(3) Eclipse - 蒼井翔太 - 2018-05-09\n"
            + "(4) この道を/会いに行く/坂道を上って/小さな風景 - 小田和正 - 2018-05-02\n"
            + "(5) THE IDOLM@STER SideM WORLD TRE@SURE 01(永遠(とわ)なる四銃士) - "
            + "天道輝(仲村宗悟),葛之葉雨彦(笠間淳),握野英雄(熊谷健太郎),紅井朱雀(益山武明) - "
            + "2018-05-09\n"
            + "(6) 誓い - 雨宮天 - 2018-05-09\n"
            + "(7) アップデート - miwa - 2018-05-09\n"
            + "(8) 泣けないぜ…共感詐欺/Uraha=Lover/君だけじゃないさ...friends"
            + "(2018アコースティックVer.) - アンジュルム - 2018-05-09\n"
            + "(9) 泡沫夢幻・胡蝶刃 〜GRANBLUE FANTASY〜 - ナルメア(M・A・O) - 2018-05-02\n"
            + "(10) Crosswalk/リワインド - 鈴木みのり - 2018-05-09";

    @Test
    public void testInstanceOf() {
        assertTrue(command instanceof DailyRankCommand);
    }

    @Test
    public void testValidOutput() throws IOException {
        reply = command.execute(testDate);

        assertEquals(sampleDailyOutput,reply.getText());
    }

}
