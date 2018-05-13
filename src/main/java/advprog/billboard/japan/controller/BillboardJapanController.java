package advprog.billboard.japan.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.logging.Logger;



@LineMessageHandler
public class BillboardJapanController {
    private static final Logger LOGGER = Logger.getLogger(BillboardJapanController.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        try {
            if (content.getText().equals("/billboard japan100")) {
                String reply = getChartBillboard();
                return new TextMessage(reply);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            return new TextMessage("Command not found!");
        } catch (IOException e){
            return new TextMessage("Billboard error");
        }
    }

    private String getChartBillboard() throws IOException{
        Document document = Jsoup.connect("https://www.billboard.com/charts/japan-hot-100").get();
        Elements elements = document.select(".chart-row__title");
        StringBuilder stringBuilder = new StringBuilder();
        for(int urutan=0;urutan<10;urutan++){
            Element element = elements.get(urutan);
            stringBuilder.append("(").append(urutan+1).append(")")
                    .append(element.select(".chart-row__artist").text()).append("  -  ").append(element.select(".chart-row__song").text())
                    .append("\n");
        }
        LOGGER.info(stringBuilder.toString());
        return stringBuilder.toString();
    }
    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }


}
