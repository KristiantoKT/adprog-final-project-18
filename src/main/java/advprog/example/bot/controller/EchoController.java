package advprog.example.bot.controller;

import advprog.example.bot.cgv.CgvStudioType;
import advprog.example.bot.cgv.ChangeCinema;
import advprog.example.bot.cgv.DefaultCinema;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.logging.Logger;

@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());
    private static CgvStudioType cgv;

    static {
        cgv = new CgvStudioType();
        cgv.setState(new DefaultCinema());
    }

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        if (contentText.contains("/cgv")) {

            if (contentText.contains("/cgv_change_cinema")) {
                String before = cgv.getState().cinemaName();

                String url = contentText.replace("/cgv_change_cinema", "");

                if (urlMatcher(url)) {
                    ChangeCinema changeCinema =
                            new ChangeCinema(url);

                    cgv.setState(changeCinema);
                    String after = cgv.getState().cinemaName();

                    return new TextMessage(
                            String.format("Congratulations ! Your Cinema Default change from %s to %s\nNow you can search the best schedule for you !", before, after)
                    );
                } else {
                    return new TextMessage("Url is invalid");
                }
            } else if (contentText.contains("/cgv_gold_class")) {
                return new TextMessage(cgv.cgvGoldClass());
            } else if (contentText.contains("/cgv_regular_2d")) {
                return new TextMessage(cgv.cgvRegular2d());
            } else if (contentText.contains("/cgv_4dx_3d_cinema")) {
                return new TextMessage(cgv.cgv4DxCinema());
            } else if (contentText.contains("/cgv_velvet")) {
                return new TextMessage(cgv.cgvVelvet());
            } else if (contentText.contains("/cgv_sweet_box")) {
                return new TextMessage(cgv.cgvSweetBox());
            }
        }

        if(contentText.contains("/echo")) {

            String replyText = contentText.replace("/echo", "");
            return new TextMessage(replyText.substring(1));
        } else {
            return new TextMessage("Command Not Found ! Please input the right command ");
        }

    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    private boolean urlMatcher(String url) {
        return url.contains("https://www.cgv.id/en/schedule/cinema/");
    }
}
