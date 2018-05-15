package advprog.example.bot.controller;

import advprog.speechtotext.bot.FetchStuff;
import advprog.speechtotext.bot.Text;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.IOException;
import java.util.logging.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());
    private static boolean canDoMethod = false;
    private static String lineApiWebsite = "https://api.line.me/v2/bot/message/%s/content";
    private static String tokenLine = "duWG8dlipcxIsMdED5m4cUoLkEbg"
            + "JVjEOIRamugQO9ejarfMaAV2RFXZ20uRgEOSxrRijyYTX0S9iUFHTAjv"
            + "gKLGChfoGe3ikLuWA2Ja1+nv7jeI3kpCQ61bW8ZaVBbiZkpxEQBVz20Q4"
            + "Tps+TbICgdB04t89/1O/w1cDnyilFU=";

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
            replyText = "Ready to recognize speech. Due to the "
                    + "incompleteness of the current program, "
                    + "please make sure the sound file is type .wav and "
                    + "the length is less than 10 seconds.";
            canDoMethod = true;
        }
        return new TextMessage(replyText);
    }

    @EventMapping
    public TextMessage handleAudioMessageEvent(MessageEvent<AudioMessageContent> event) {
        LOGGER.fine(String.format("AudioMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        AudioMessageContent content = event.getMessage();
        String contentId = content.getId();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + tokenLine);
        HttpEntity<String> httpEntity = new HttpEntity<String>("", httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        String contentLink = String.format(lineApiWebsite, contentId);
        ResponseEntity<byte[]> kembalian = restTemplate.exchange(contentLink,
                HttpMethod.GET,
                httpEntity,
                byte[].class);
        Text textKembalian = new Text("Belum ada apa-apa");
        try {
            textKembalian.setSpeechText(FetchStuff.getTextFromSpeech(kembalian.getBody())
                    .getSpeechText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new TextMessage(String.valueOf(kembalian.getBody().length));
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
