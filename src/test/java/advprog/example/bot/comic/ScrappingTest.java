package advprog.example.bot.comic;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

public class ScrappingTest {
    Scrapping scrapping;
    String monthlyApril = "Top 10\n"
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

    String testUrl = "https://www.oricon.co.jp/rank/cbm/m/2018-04/";

    String scrapOut;

    public ScrappingTest() throws IOException {
        scrapping = new Scrapping();
    }

    @Test
    public void constructorTest() {
        assertNotNull(scrapping);
    }

    @Test
    public void sampleTest() throws IOException {
        scrapOut = scrapping.scrap(testUrl);
        assertEquals(monthlyApril,scrapOut);
    }

}
