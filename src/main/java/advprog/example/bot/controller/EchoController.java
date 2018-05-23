package advprog.example.bot.controller;

import advprog.acronym.bot.Acronym;
import advprog.acronym.bot.AcronymOperations;
import advprog.example.bot.BotExampleApplication;
import advprog.speechtotext.bot.FetchStuff;
import advprog.speechtotext.bot.Text;
import com.google.common.io.ByteStreams;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.postback.PostbackContent;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
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
import java.util.*;
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
    static boolean canDoMethod = false;
    static File file = new File("acronyms/acronyms.csv");
    static boolean terimaKependekan = false;
    static boolean terimaKepanjangan = false;
    String kependekan;
    String kepanjangan;
    ArrayList<Acronym> acronyms;
    {
        try {
            acronyms = AcronymOperations.addToArrayList(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws IOException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String replyText = "";
        if (terimaKependekan) {
            kependekan = contentText;
            terimaKepanjangan = true;
            terimaKependekan = false;
            replyText = "Silakan masukkan kepanjangan";

        } else if (terimaKepanjangan) {
            kepanjangan = contentText;
            terimaKepanjangan = false;
            Acronym newAcronym = new Acronym(kependekan, kepanjangan);
            AcronymOperations.add(newAcronym, file);
            acronyms.add(newAcronym);
            replyText = kependekan + " - " + kepanjangan + " Berhasil ditambah";

        } else {
            if (contentText.contains("/echo")) {
                replyText = contentText.replace("/echo ", "");
            } else if (contentText.contains("/speech-to-text")) {
                replyText = "Ready to recognize speech. Due to the "
                        + "incompleteness of the current program, "
                        + "please make sure the sound file is type .wav and "
                        + "the length is less than 10 seconds.";
                canDoMethod = true;
            } else if (contentText.contains("/add_acronym")) {
                replyText = "Silakan masukkan kependekan";
                terimaKependekan = true;
            } else if (contentText.contains("/delete_acronym")) {
                List<CarouselColumn> carouselColumns = new ArrayList<>();
                for (int i = 0; i < acronyms.size(); i++) {
                    carouselColumns.add(new CarouselColumn(null,
                            acronyms.get(i).getKependekan(),
                            acronyms.get(i).getKepanjangan(),
                            Arrays.asList(new PostbackAction("Delete", "Delete "
                                    + i))));
                }
                CarouselTemplate carouselTemplate = new CarouselTemplate(carouselColumns);
                TemplateMessage templateMessage = new TemplateMessage("Delete this", carouselTemplate);
                reply(event.getReplyToken(), templateMessage);
                replyText = "Silakan pilih yang mau didelete dari carousel";
            } else if (contentText.contains("/update_acronym")) {
                List<CarouselColumn> carouselColumns = new ArrayList<>();
                for (int i = 0; i < acronyms.size(); i++) {
                    carouselColumns.add(new CarouselColumn(null,
                            acronyms.get(i).getKependekan(),
                            acronyms.get(i).getKepanjangan(),
                            Arrays.asList(new PostbackAction("Update", "Update "
                                    + i))));
                }
                CarouselTemplate carouselTemplate = new CarouselTemplate(carouselColumns);
                TemplateMessage templateMessage = new TemplateMessage("Update this", carouselTemplate);
                reply(event.getReplyToken(), templateMessage);
                replyText = "Silakan pilih yang mau diupdate dari carousel";
            }
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
        if(canDoMethod) {
            handleHeavyContent(
                event.getReplyToken(),
                event.getMessage().getId(),
                responseBody -> {
                    DownloadedContent wav = saveContent("wav", responseBody);
                    File file = new File(wav.path.toString());
                    FileInputStream fileInputStream;
                    Text text = new Text("Gaada");
                    try {
                        fileInputStream = new FileInputStream(file);
                        byte[] byteArray = new byte[(int) file.length()];
                        fileInputStream.read(byteArray);
                        text.setSpeechText(FetchStuff.getTextFromSpeech(byteArray).getSpeechText());
                    } catch (IOException e) {
                        LOGGER.fine(e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                    reply(event.getReplyToken(), new TextMessage(text.getSpeechText()
                            != null || text.getSpeechText() != "" ? text.getSpeechText() : "Gaada"));
                    canDoMethod = false;
                });
        } else {
            reply(event.getReplyToken(), new TextMessage("tulis /speech-to-text "
                    + "terlebih dahulu"));
        }
        //        return new TextMessage(textKembalian.getSpeechText() != null
        //                ? textKembalian.getSpeechText() : "Tidak ada");
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    @EventMapping
    public void handlePostback(PostbackEvent event) {
        PostbackContent postbackContent = event.getPostbackContent();
        String postbackCommand = postbackContent.getData();
        String[] postbackCommandSplit = postbackCommand.split(" ");
//        if(postbackCommandSplit[0].equals("Delete")) {
//
//        }
//        else if(postbackCommandSplit[0].equals("Update")) {
//
//        }

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
