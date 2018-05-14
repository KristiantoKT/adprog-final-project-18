package advprog.example.bot.oricon.command;

import advprog.example.bot.oricon.comic.Scrapping;
import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public class MonthlyCommand implements Command {
    private Scrapping scrapping = new Scrapping();

    static final String MONTHLY_URL = "https://www.oricon.co.jp/rank/cbm/m/";

    @Override
    public TextMessage execute(String input) throws IOException {
        String out = scrapping.scrap(MONTHLY_URL + input + "/");

        return new TextMessage(out.contains("Invalid")
                ? "I didn't find any rankings for " + input : out);
    }
}
