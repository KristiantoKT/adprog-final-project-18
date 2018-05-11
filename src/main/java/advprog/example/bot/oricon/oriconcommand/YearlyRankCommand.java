package advprog.example.bot.oricon.oriconcommand;

import com.linecorp.bot.model.message.TextMessage;
import java.io.IOException;
import advprog.example.bot.oricon.oriconsingle.Scrapper;


public class YearlyRankCommand implements RankCommand {

    private Scrapper scrapper = new Scrapper();

    private static final String YEARLY_URL = "https://www.oricon.co.jp/rank/js/y/";

    @Override
    public TextMessage execute(String input) throws IOException {
        String out = scrapper.scrap(YEARLY_URL + input + "/");

        return new TextMessage(out.contains("Invalid")
                ? "I didn't find any rankings for " + input : out);
    }
}
