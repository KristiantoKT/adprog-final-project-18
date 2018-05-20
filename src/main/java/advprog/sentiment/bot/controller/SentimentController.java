package advprog.sentiment.bot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;

import org.springframework.boot.json.BasicJsonParser;

@LineMessageHandler
public class SentimentController {

    private static final Logger LOGGER = Logger.getLogger(SentimentController.class.getName());

    private static final String API_URL = "https://southeastasia.api.cognitive.microsoft.com/text/analytics/v2.0/sentiment";
    private static final String SUBCRIPTION_KEY = "122c5a4b68f6430d9d9145692799bbd5";

    @EventMapping
    @SuppressWarnings("unchecked")
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
            String responseText = getSentiment(tmp);

            BasicJsonParser parser = new BasicJsonParser();

            Map<String, Object> data0 = parser.parseMap(responseText);
            List<Object> data1 = (List<Object>) data0.get("documents");
            Map<String, Object> data2 = (Map<String, Object>) data1.get(0);

            System.out.println(data2.get("score"));
            double data5 = (double)data2.get("score");
            double score = Double.valueOf(data5) * 100;
            System.out.println(score);

            replyText = String.format("Sentiment: %.2f%%", score);
        } else {
            replyText = "";
        }

        return new TextMessage(replyText);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    public String getSentiment(String text) {
        String query = "{"
                + 
                "\"documents\" : ["
                    + "{"
                        + "\"languange\": \"%s\", "
                        + "\"id\": \"%s\", "
                        + "\"text\": \"%s\""
                    + "}"
                + "]"
            + "}";

        String escapedText = text.replace("\"", "\\\"");
        escapedText = text.replace("\'", "\\\'");

        query = String.format(query, "en", "1", escapedText);

        String result = "";
        try {
            URL url = new URL(API_URL);

            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

            con.setRequestMethod("POST");
            con.addRequestProperty("Content-Type", "text/json");
            con.setRequestProperty("Ocp-Apim-Subscription-Key", SUBCRIPTION_KEY);
            con.setDoOutput(true);

            byte[] encodedQuery = query.getBytes("UTF-8");

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.write(encodedQuery, 0, encodedQuery.length);
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
