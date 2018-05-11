package advprog.handwrittenrecognitionbot.controller;

import advprog.handwrittenintotext.HandwrittingRecognition;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@LineMessageHandler
public class HandwrittenRecognitionBot {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    private RestTemplate rest = new RestTemplate();
    private HandwrittingRecognition rec = new HandwrittingRecognition();
    private String urlImage;

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String[] arrContentText = contentText.split(" ");
        if(arrContentText.length == 2 && arrContentText[0].equalsIgnoreCase("ocr")
                && arrContentText[1].equalsIgnoreCase("this")) {
            String message = getStringFromImage();
            String replyToken = event.getReplyToken();
            return sendMessage(replyToken, message);
        }
        return null;
    }

    @EventMapping
    public void handleImageMessageEvent(MessageEvent<ImageMessageContent> event) {
        ImageMessageContent content = event.getMessage();
        String id = content.getId();
        String url = "https://api.line.me/v2/bot/message/"+id+"/content";
        ImageFromLine image = rest.getForObject(url, ImageFromLine.class);
        urlImage = image.getOriginalContentUrl();
    }

    /**
    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
    */

    private String getStringFromImage() {
        return rec.convertImageToString(urlImage);
    }

    private TextMessage sendMessage(String replyToken, String result) {
        TextMessage message = new TextMessage(result);
        try{
            lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, message))
                    .get();
        } catch (Exception e){
            System.out.println("ERROR");
        }
        return message;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}