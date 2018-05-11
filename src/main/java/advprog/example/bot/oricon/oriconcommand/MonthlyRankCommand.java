package advprog.example.bot.oricon.oriconcommand;

import advprog.example.bot.oricon.oriconsingle.Scrapper;
import com.linecorp.bot.model.message.TextMessage;
import java.io.IOException;


public class MonthlyRankCommand implements RankCommand {

    private Scrapper scrapper = new Scrapper();

    private static final String MONTHLY_URL = "https://www.oricon.co.jp/rank/js/m/";

    @Override
    public TextMessage execute(String input) throws IOException {
        String out = scrapper.scrap(MONTHLY_URL + input + "/");

        return new TextMessage(out.contains("Invalid")
                ? "I didn't find any rankings for " + input : out);
    }
}
