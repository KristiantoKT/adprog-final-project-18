package advprog.example.bot.oricon.command;

import com.linecorp.bot.model.message.TextMessage;

import java.io.IOException;

public interface Command {
    TextMessage execute(String input) throws IOException;
}


