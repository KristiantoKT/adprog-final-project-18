package advprog.example.bot.controller;

import advprog.acronym.bot.Acronym;
import advprog.acronym.bot.AcronymOperations;
import advprog.example.bot.BotExampleApplication;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
    static File file = new File("acronyms/acronyms.csv");
    static boolean terimaKependekan = false;
    static boolean terimaKepanjangan = false;
    static boolean flagDelete = false;
    static boolean flagUpdate = false;
    String kependekan;

    String kepanjangan;
    static Acronym yangDicari;
    ArrayList<Acronym> acronyms;

    {
        try {
            acronyms = AcronymOperations.addToArrayList(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event)
            throws IOException {
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
        } else if (flagDelete) {
            if (contentText.toLowerCase().contains("yes")) {
                AcronymOperations.delete(yangDicari, file);
                acronyms = AcronymOperations.addToArrayList(file);
                replyText = yangDicari.getKependekan() + " - " + yangDicari.getKepanjangan()
                        + " Telah didelete";
            } else if (contentText.toLowerCase().contains("no")) {
                replyText = yangDicari.getKependekan() + " - " + yangDicari.getKepanjangan()
                        + " Gagal didelete";
            }
            flagDelete = false;

        } else if (flagUpdate) {
            String kepanjanganBaru = contentText;
            AcronymOperations.update(yangDicari, kepanjanganBaru, file);
            acronyms = AcronymOperations.addToArrayList(file);
            flagUpdate = false;
            replyText = yangDicari.getKependekan() + " - " + yangDicari.getKepanjangan()
                    + " Berhasil diubah";
        } else {
            if (contentText.contains("/echo")) {
                replyText = contentText.replace("/echo ", "");
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
                CarouselTemplate carouselTemplate =
                        new CarouselTemplate(carouselColumns);
                TemplateMessage templateMessage = new TemplateMessage("Delete this",
                        carouselTemplate);
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
                TemplateMessage templateMessage = new TemplateMessage("Update this",
                        carouselTemplate);
                reply(event.getReplyToken(), templateMessage);
                replyText = "Silakan pilih yang mau diupdate dari carousel";
            }
        }
        return new TextMessage(replyText);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    @EventMapping
    public void handlePostback(PostbackEvent event) throws IOException {
        PostbackContent postbackContent = event.getPostbackContent();
        String postbackCommand = postbackContent.getData();
        String[] postbackCommandSplit = postbackCommand.split(" ");
        if (postbackCommandSplit[0].equals("Delete")) {
            int index = Integer.parseInt(postbackCommandSplit[1]);
            yangDicari = acronyms.get(index);
            flagDelete = true;
            reply(event.getReplyToken(), new TextMessage("Are you sure?"));
        } else if (postbackCommandSplit[0].equals("Update")) {
            int index = Integer.parseInt(postbackCommandSplit[1]);
            yangDicari = acronyms.get(index);
            flagUpdate = true;
            reply(event.getReplyToken(), new TextMessage("Silakan masukkan kepanjangan"));
        }

    }

    public void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    public void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages))
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            return;
        }
    }
}
