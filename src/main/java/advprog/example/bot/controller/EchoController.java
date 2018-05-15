package advprog.example.bot.controller;

import advprog.example.bot.oricon.OriconBooks;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import org.jsoup.Jsoup;

@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String replyText;
        if (contentText.contains("/oricon books weekly")) {
            String[] input = contentText.split(" ");
            String date = input[3];
            String url = "https://oricon.co.jp/rank/ob/w/";
            String newUrl = url + date + "/";

            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                simpleDateFormat.setLenient(false);
                simpleDateFormat.parse(date);

                //checking for connection
                Jsoup.connect(newUrl).execute();

                OriconBooks oriconBooks = new OriconBooks(url, date);
                replyText = oriconBooks.printTopTenList();
            } catch (ParseException | IllegalArgumentException e) {
                replyText = "Command is incorrect. Please insert /oricon books weekly yyyy-MM-dd";
            } catch (IOException e) {
                replyText = "Date is unavailable. Please change the date";
            }
        } else {
            replyText = "Command not found!";
        }
        return new TextMessage(replyText);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
