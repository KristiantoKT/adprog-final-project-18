package zonkbot.controller;

import com.google.gson.Gson;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import zonkbot.Question;
import zonkbot.Zonkbot;

@LineMessageHandler
public class ZonkbotController {

    private static final Logger LOGGER = Logger.getLogger(ZonkbotController.class.getName());
    public Zonkbot zonkbot = new Zonkbot();
    private Question question = null;


   private ReplyController reply;

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws IOException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent messageContent = event.getMessage();
        String textContent = messageContent.getText();
        String replyText = zonkbot.responseMessage(textContent, event.getReplyToken(), this);
        if(!replyText.isEmpty())
            reply.replyText(event.getReplyToken(),replyText);
        else
            reply.replyText(event.getReplyToken(), "masuk ke class pertama");
    }



    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }



    public static void writeToJson(Question question) throws IOException {
        Gson gson = new Gson();
        ArrayList<Question> questions = readFromJSON();
        questions.add(question);
        try (FileWriter writer = new FileWriter("src/file.json")) {
            gson.toJson(questions,writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Question> readFromJSON() throws IOException {
        Gson gson = new Gson();
        Question[] result = null;

        try (Reader reader = new FileReader("src/file.json")) {
            result = gson.fromJson(reader, Question[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Question> resultList = new ArrayList<Question>(Arrays.asList(result));
        return resultList;
    }
}
