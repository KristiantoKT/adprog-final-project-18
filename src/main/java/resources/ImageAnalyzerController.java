package resources;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

@LineMessageHandler
public class ImageAnalyzerController {

    private static final Logger LOGGER = Logger.getLogger(ImageAnalyzerController.class.getName());

    private byte[] contentBytes = null;

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String replyText;
        if (contentText.equalsIgnoreCase("/analyse_picture")) {
            if (contentBytes != null) {
                ImageAnalyzer imageAnalyzer = new ImageAnalyzer(contentBytes);
                replyText = imageAnalyzer.getResult();
            } else {
                replyText = "Masukkan gambar yang ingin di analisa";
            }
        } else {
            replyText = "ngomong apa sih bro";
        }
        return new TextMessage(replyText);
    }

    @EventMapping
    public void handleImageMessageEvent(MessageEvent<ImageMessageContent> event) throws IOException {
        final LineMessagingClient client = LineMessagingClient
                .builder(System.getProperty("line.bot.channelToken"))
                .build();

        final MessageContentResponse messageContentResponse;
        try {
            messageContentResponse = client.getMessageContent(event.getMessage().getId()).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }
        InputStream contentStream = messageContentResponse.getStream();
        contentBytes = IOUtils.toByteArray(contentStream);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }
}
