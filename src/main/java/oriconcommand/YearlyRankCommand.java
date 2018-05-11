package oriconcommand;

import com.linecorp.bot.model.message.TextMessage;
import oriconsingle.Scrapper;

import java.io.IOException;


public class YearlyRankCommand implements RankCommand{

    private Scrapper scrapper = new Scrapper();

    private static final String YEARLY_URL = "https://www.oricon.co.jp/rank/js/y/";

    @Override
    public TextMessage execute(String input) throws IOException{
        String out = scrapper.scrap(YEARLY_URL+input+"/");

        return new TextMessage(out.contains("Invalid") ?
                "I didn't find any rankings for " + input : out);
    }
}
