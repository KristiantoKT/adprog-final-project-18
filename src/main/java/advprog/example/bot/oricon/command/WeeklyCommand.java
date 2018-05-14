package advprog.example.bot.oricon.command;

import advprog.example.bot.oricon.comic.Scrapping;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;


public class WeeklyCommand implements Command {
    private Scrapping scrapping = new Scrapping();

    static final String WEEKLY_URL = "https://www.oricon.co.jp/rank/cbm/w/";

    @Override
    public TextMessage execute(String input) throws IOException {
        String out = scrapping.scrap(WEEKLY_URL + input + "/");

        return new TextMessage(out.contains("Invalid")
                ? "I didn't find any rankings for " + input : out);
    }
}
