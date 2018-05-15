package advprog.example.bot.controller;

import advprog.handwrittenintotext.HandwrittingRecognition;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());
    private RestTemplate rest = new RestTemplate();
    private HandwrittingRecognition rec = new HandwrittingRecognition();
    private String urlImage;

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String[] arrContentText = contentText.split(" ");
        if (arrContentText.length == 2 && arrContentText[0].equalsIgnoreCase("ocr")
                && arrContentText[1].equalsIgnoreCase("this")) {
            String message = getStringFromImage();
            return new TextMessage(message);
        } else if (arrContentText[0].equalsIgnoreCase("/echo")) {
            return new TextMessage(arrContentText[1]);
        } else{
            return new TextMessage("[ERROR] Command not found");
        }
    }

    @EventMapping
    public void handleImageMessageEvent(MessageEvent<ImageMessageContent> event) {
        ImageMessageContent content = event.getMessage();
        String id = content.getId();
        String url = "https://api.line.me/v2/bot/message/" + id + "/content";
        ImageFromLine image = rest.getForObject(url, ImageFromLine.class);
        urlImage = image.getOriginalContentUrl();
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    private String getStringFromImage() {
        return rec.convertImageToString(urlImage);
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
