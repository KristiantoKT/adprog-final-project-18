package zonkbot.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import zonkbot.Question;
import zonkbot.Zonkbot;

@LineMessageHandler
public class ZonkbotController {

    private static final Logger LOGGER = Logger.getLogger(ZonkbotController.class.getName());
    private Zonkbot zonkbot = null;
    private Question question = null;
    private boolean zonkbotActive = false;


    @Autowired
    private LineMessagingClient lineMessagingClient;

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent messageContent = event.getMessage();
        String textContent = messageContent.getText();
        String replyText = responseMessage(textContent);
        replyText(event.getReplyToken(),replyText);
        return;
    }

    private String responseMessage(String textContent) {
        String replyText;
        //ACTIVATE ZONKBOT
        if (textContent.equals("/zonkbot") && !zonkbotActive) {
            replyText = activateZonkbot();
        }
        //ZONKBOT ALREADY ACTIVATED
        else if (textContent.equals("/zonkbot") && zonkbotActive) {
            replyText = "zonkbot has already activated";
        }
        //DEACTIVATE ZONKBOT
        else if (textContent.equals("/deactivate_zonkbot") && zonkbotActive) {
            zonkbotActive = false;
            replyText = "zonkbot deactivated, all question will be deleted";
        }
        //ADD_QUESTION
        else if (textContent.equals("/add_question") && zonkbotActive
                && !zonkbot.isAdd_question_section()) {
            replyText = "Please input your question";
            zonkbot.setAdd_question_section(true);
        }
        //ADD_QUESTION_SECTION
        else if (zonkbotActive && zonkbot.isAdd_question_section()) {
            replyText = add_question(textContent);
        }
        //ECHO
        else if (textContent.substring(0,5).equals("/echo")) {
            replyText =  textContent.replace("/echo","");
        }
        //OTHERS
        else {
            replyText = "zonkbot are not available."
                    + "To activate zonkbot please type \"/zonkbot\"";
        }
        return replyText;
    }

    @NotNull
    private String activateZonkbot() {
        String replyText;
        zonkbotActive = true;
        zonkbot = new Zonkbot();
        replyText = "zonkbot activated!";
        return replyText;
    }


    public String add_question(String textContent) {
        String result = "";
        int answerNumber = zonkbot.getAnswer_number();
        if (answerNumber == 0) {
            question = new Question(textContent);
            result = "Answer 1:";
            zonkbot.setAnswer_number(++answerNumber);
        } else if (answerNumber < 4) {
            zonkbot.setAnswer_number(++answerNumber);
            question.addAnswer(textContent);
            result = "Answer " + answerNumber + ":";
        } else if (answerNumber > 4) {
            zonkbot.add_question(question);
            result = zonkbot.toString();
            questionReset();
        }
        return result;
    }

    private void questionReset() {
        zonkbot.setAnswer_number(0);
        zonkbot.setAdd_question_section(false);
        question = null;
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void replyText(@NonNull String replyToken, @NonNull String message) {
        if (replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken must not be empty");
        }
        if (message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "……";
        }
        this.reply(replyToken, new TextMessage(message));
    }
}
