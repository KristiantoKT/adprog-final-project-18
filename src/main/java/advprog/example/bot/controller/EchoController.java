package advprog.example.bot.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());
    private static boolean canDoMethod = false;
    private static String lineApiWebsite = "https://api.line.me/v2/bot/message/%s/content";
    private static String tokenLine = "PvCV0Md5g6W+rui3/oiRDKksMnFJq9Ok2ZIgykqstv0" +
            "zb2s/kXE27b95EzspEa3WxrRijyYTX0S9iUFHTAjvgK" +
            "LGChfoGe3ikLuWA2Ja1+kN1mP27Xe9BKjNbM" +
            "wh83ng9AgJuIdPqfAmbhKAF9Z2WwdB04t89/1O/w1cDnyilFU=";

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String replyText = "";
        if (contentText.contains("/echo")) {
            replyText = contentText.replace("/echo ", "");
        } else if (contentText.contains("/speech-to-text")) {
            replyText = "Ready to recognize speech. Due to the " +
                    "incompleteness of the current program, " +
                    "please make sure the sound file is type .wav and " +
                    "the length is less than 10 seconds.";
            canDoMethod = true;
        }
        return new TextMessage(replyText);
    }

    @EventMapping
    public TextMessage handleAudioMessageEvent(MessageEvent<AudioMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        AudioMessageContent content = event.getMessage();
        String contentId = content.getId();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + tokenLine);
        HttpEntity<String> httpEntity = new HttpEntity<String>("", httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        String contentLink = String.format(lineApiWebsite, contentId);
        ResponseEntity<String> kembalian = restTemplate.exchange(contentLink,
                HttpMethod.GET,
                httpEntity,
                String.class);
        return new TextMessage(kembalian.getBody());
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
