package advprog.example.bot.controller;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@LineMessageHandler
public class ItunesController {

    private static final Logger LOGGER = Logger.getLogger(ItunesController.class.getName());
    private static final String channelToken = "M+cDBxVHqt8ZjGEcYDqy7X3HX/SECVSx6xVDXoaKh1l5mMy00ZZ9Y8cJS5o7H2AqieBj8UbU9bfPNYQqI4JXE8EJgyuaaJLJB8Oo21K2XN4NJZOQgK3ouBglkK7V0K7iVNqz27a1xKahlIODN6qRigdB04t89/1O/w1cDnyilFU=";

    @Autowired
    public LineMessagingClient lineMessagingClient;

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws IOException, JSONException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String[] contentTextArr = contentText.split(" ");

        if (contentTextArr[0].equalsIgnoreCase("/itunes_preview")) {
            String[] param = Arrays.copyOfRange(contentTextArr, 1, contentTextArr.length);
            JSONObject jsonData = connectApi(param);
            SongInformation theSong = getSongInformation(jsonData);
            replyAudio(event.getReplyToken(), theSong);
        }

        return new TextMessage("Wrong input! Please use /itunes_preview ARTIST");
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    public static JSONObject connectApi(String[] artistName) throws IOException, JSONException {
        String theUrl = urlBuilder(artistName);
        URL url = new URL(theUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject json = new JSONObject(response.toString());
        JSONObject songData = getRandomAlbum(json);

        return songData;
    }

    public static JSONObject getRandomAlbum(JSONObject temp) throws JSONException {
        JSONArray dataExtract = (JSONArray) temp.get("results");
        Random rand = new Random();

        int n = rand.nextInt(dataExtract.length());
        JSONObject randomAlbum = (JSONObject) dataExtract.get(n);
        return randomAlbum;
    }

    public SongInformation getSongInformation(JSONObject data) throws JSONException {

        String trackName = (String) data.get("trackName");
        String artistname = (String) data.get("artistName");
        String preview = (String) data.get("previewUrl");
        SongInformation theSong = new SongInformation(
                artistname, trackName, preview
        );

        return theSong;
    }

    public static String urlBuilder(String[] param) {
        String base = "https://itunes.apple.com/search?term=";
        for (int i = 0; i < param.length; i++) {
            base += param[i] + "+";
        }
        return base.substring(0,base.length()-1);
    }

    public void replyAudio(String replyToken, SongInformation theSong) {

        lineMessagingClient = LineMessagingClient
                .builder(channelToken)
                .build();
        String content = "* Artist Name : " + theSong.artistName + "\n"
                + "* Track Name : " + theSong.trackName + "\n";

        final TextMessage textMessage = new TextMessage(content);

        final AudioMessage audioMessage = new AudioMessage(theSong.previewUrl, 20000);

        final ImageMessage imageMessage = new ImageMessage(
                "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Download_on_iTunes.svg/800px-Download_on_iTunes.svg.png"
                ,"https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Download_on_iTunes.svg/800px-Download_on_iTunes.svg.png");

        List<Message> message = new ArrayList<Message>();
        message.add(imageMessage);
        message.add(textMessage);
        message.add(audioMessage);

        final ReplyMessage replyMessage = new ReplyMessage(
                replyToken,
                message);

        final BotApiResponse botApiResponse;
        try {
            botApiResponse = lineMessagingClient.replyMessage(replyMessage).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }
    }

    public class SongInformation {
        String previewUrl;
        String trackName;
        String artistName;

        public SongInformation(String artist, String track, String url) {
            this.previewUrl = url;
            this.artistName = artist;
            this.trackName = track;
        }
    }
}
