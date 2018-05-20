package advprog.sentiment.bot.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import org.springframework.boot.json.GsonJsonParser;

import java.util.logging.Logger;
import java.util.Map;

import java.net.URL;
import java.net.MalformedURLException;
import javax.net.ssl.HttpsURLConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.io.IOException;


@LineMessageHandler
public class SentimentController {

    private static final Logger LOGGER = Logger.getLogger(SentimentController.class.getName());

    private static final String API_URL = "https://southeastasia.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment";
    private static final String SUBCRIPTION_KEY = "122c5a4b68f6430d9d9145692799bbd5";

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String replyText;
        if (contentText.matches("^/echo (.*)")) {
            replyText = contentText.replace("/echo ", "");
        } else if (contentText.matches("^/sentiment (.*)")) {
            String tmp = contentText.replace("/sentiment ", "");
            replyText = getSentiment(tmp);

            GsonJsonParser parser = new GsonJsonParser();

            Map<String, Object> data = parser.parseMap(replyText);

            System.out.println(data.get("documents"));
        } else {
            replyText = "wait what?";
        }

        return new TextMessage(replyText);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    public String getSentiment(String text) {
        String query = 
            "{" +
                "\"documents\" : [" + 
                    "{" +
                        "\"languange\": \"%s\", " +
                        "\"id\": \"%s\", " +
                        "\"text\": \"%s\"" +
                    "}" + 
                "]" + 
            "}";

        query = String.format(query, "en", "1", text);

        String result = "";
        try {
            URL url = new URL(API_URL);

            LOGGER.fine(String.format("what do i know? this is : \n\n%s \n\n%s \n\n%s", 
                        query, "lol", "lol"));

            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

            con.setRequestMethod("POST");
            con.addRequestProperty("Content-Type", "text/json");
            con.setRequestProperty("Ocp-Apim-Subscription-Key", SUBCRIPTION_KEY);
            con.setDoOutput(true);

            byte[] encoded_query = query.getBytes("UTF-8");

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(encoded_query, 0, encoded_query.length);
            wr.flush();
            wr.close();

            if (con != null) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream())
                        );
                String next;
                while ((next = br.readLine()) != null) {
                    result += next;
                }

                br.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
