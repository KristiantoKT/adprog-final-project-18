package advprog.example.bot.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;


@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());

    @Autowired
    LineMessagingClient lineMessagingClient;

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));

        String url = "https://www.cgv.id/en/schedule/cinema";
        MovieSchedules movieSched = new MovieSchedules(url);

        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String replyText = "";

        if (contentText.contains("/cgv_gold_class")){
            replyText += contentText;
            return new TextMessage(movieSched.findArtist(replyText));
        } else if (contentText.contains("/cgv_regular_2d")) {
            replyText += contentText;
            return new TextMessage(movieSched.findArtist(replyText));
        } else if (contentText.contains("/cgv_4dx_3d_cinema")) {
                replyText += contentText;
                return new TextMessage(movieSched.findArtist(replyText));
        } else if (contentText.contains("/cgv_velvet")) {
            replyText += contentText;
            return new TextMessage(movieSched.findArtist(replyText));
        } else if (contentText.contains("/cgv_sweet_box")) {
            replyText += contentText;
            return new TextMessage(movieSched.findArtist(replyText));
        } else if (contentText.contains("/cgv_sweet_box")) {
            replyText += contentText;
            return new TextMessage(movieSched.findArtist(replyText));
        } else if (contentText.contains("/cgv_change_cinema")) {
            replyText += contentText.replace("/cgv_change_cinema ", "");
            url = replyText;
            return new TextMessage(movieSched.findArtist(replyText));
        }
        else {
            replyText += "Pleasa input the right command";
            return new TextMessage(movieSched.findArtist(replyText));
        }


    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    private void reply(String reply, String token) {
        TextMessage textMessage = new TextMessage(reply);
        try {
            lineMessagingClient.replyMessage(new ReplyMessage(token, textMessage)).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error");
        }
    }

}


