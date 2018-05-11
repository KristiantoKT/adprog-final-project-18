package advprog.example.bot.oricon.oriconcommand;

import advprog.example.bot.oricon.oriconsingle.Scrapper;
import com.linecorp.bot.model.message.TextMessage;
import java.io.IOException;


public class WeeklyRankCommand implements RankCommand {

    private Scrapper scrapper = new Scrapper();

    private static final String WEEKLY_URL = "https://www.oricon.co.jp/rank/js/w/";

    @Override
    public TextMessage execute(String input) throws IOException {
        String out = scrapper.scrap(WEEKLY_URL + input + "/");

        return new TextMessage(out.contains("Invalid")
                ? "I didn't find any rankings for " + input : out);
    }
}