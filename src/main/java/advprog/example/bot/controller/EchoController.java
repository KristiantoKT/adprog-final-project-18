package advprog.example.bot.controller;

import advprog.example.bot.BotExampleApplication;
import advprog.speechtotext.bot.FetchStuff;
import advprog.speechtotext.bot.Text;
import com.google.common.io.ByteStreams;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@LineMessageHandler
public class EchoController {

    @Autowired
    private LineMessagingClient lineMessagingClient;
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
    public void handleAudioMessageEvent(MessageEvent<AudioMessageContent> event) {
        //        LOGGER.fine(String.format("AudioMessageContent(timestamp='%s',content='%s')",
        //                event.getTimestamp(), event.getMessage()));
        //        AudioMessageContent content = event.getMessage();
        //        String contentId = content.getId();
        //        HttpHeaders httpHeaders = new HttpHeaders();
        //        httpHeaders.add("Authorization", "Bearer " + tokenLine);
        //        HttpEntity<String> httpEntity = new HttpEntity<String>("", httpHeaders);
        //        RestTemplate restTemplate = new RestTemplate();
        //        String contentLink = String.format(lineApiWebsite, contentId);
        //        ResponseEntity<byte[]> kembalian = restTemplate.exchange(contentLink,
        //                HttpMethod.GET,
        //                httpEntity,
        //                byte[].class);
        //        Text textKembalian = new Text("");
        //        try {
        //            textKembalian.setSpeechText(FetchStuff.getTextFromSpeech(kembalian.getBody())
        //                    .getSpeechText());
        //        } catch (IOException e) {
        //            e.printStackTrace();
        //        }
        handleHeavyContent(
            event.getReplyToken(),
            event.getMessage().getId(),
            responseBody -> {
                DownloadedContent wav = saveContent("wav", responseBody);
                File file = new File(wav.path.toString());
                FileInputStream fileInputStream;
                Text text = new Text("");
                try {
                    fileInputStream = new FileInputStream(file);
                    byte[] byteArray = new byte[(int) file.length()];
                    fileInputStream.read(byteArray);
                    text.setSpeechText(FetchStuff.getTextFromSpeech(byteArray).getSpeechText());
                } catch (IOException e) {
                    LOGGER.fine(e.getLocalizedMessage());
                    e.printStackTrace();
                }
                reply(event.getReplyToken(), new TextMessage(text.getSpeechText() != ""
                        ? text.getSpeechText() : "Gaada"));
            });
        //        return new TextMessage(textKembalian.getSpeechText() != null
        //                ? textKembalian.getSpeechText() : "Tidak ada");
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    private void handleHeavyContent(String replyToken, String messageId,
                                    Consumer<MessageContentResponse> messageConsumer) {
        final MessageContentResponse response;
        try {
            response = lineMessagingClient.getMessageContent(messageId)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        messageConsumer.accept(response);
    }

    private static DownloadedContent saveContent(String ext, MessageContentResponse responseBody) {
        DownloadedContent tempFile = createTempFile(ext);
        try (OutputStream outputStream = Files.newOutputStream(tempFile.path)) {
            ByteStreams.copy(responseBody.getStream(), outputStream);
            return tempFile;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static DownloadedContent createTempFile(String ext) {
        String fileName = LocalDateTime.now().toString() + '-' + UUID.randomUUID().toString()
                + '.' + ext;
        Path tempFile = BotExampleApplication.downloadedContentDir.resolve(fileName);
        tempFile.toFile().deleteOnExit();
        return new DownloadedContent(
                tempFile,
                createUri("/downloaded/" + tempFile.getFileName()));
    }

    private static String createUri(String path) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(path).build()
                .toUriString();
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

    public static class DownloadedContent {
        Path path;
        String uri;

        public DownloadedContent(Path path, String uri) {
            this.path = path;
            this.uri = uri;
        }
    }

}
