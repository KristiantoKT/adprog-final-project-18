package advprog.primbon.bot.controller;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

@LineMessageHandler
public class PrimbonController {
    private static final Logger LOGGER = Logger.getLogger(PrimbonController.class.getName());

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        String[] perintah  = contentText.split(" ");
        String replyText;
        if(perintah[0].equals("/primbon")){
            try{
                replyText = tanggalJawa(perintah[1]);
                return new TextMessage(replyText);
            }
            catch (ParseException e){
                return new TextMessage("input tanggal salah");
            }
        }

        else{
            return new TextMessage("Salah keyword");
        }
    }

    private String tanggalJawa(String tanggal) throws ParseException {
        String dataK = "1901-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date hari = sdf.parse(tanggal);
        Date konstan = sdf.parse(dataK);
        long a = hari.getTime() - konstan.getTime();
        long diffDay = a / (24 * 60 * 60 * 1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(hari);
        int hariKe = calendar.get(Calendar.DAY_OF_WEEK);
        return days(hariKe) + " " + weton(diffDay);
    }

    private String weton(long a) {
        if (a % 5 == 0) {
            return "Pon";
        } else if (a % 5 == 1) {
            return "Wage";
        } else if (a % 5 == 2) {
            return "Kliwon";
        } else if (a % 5 == 3) {
            return "Legi";
        } else {
            return "Pahing";
        }
    }

    private String days(int a) {
        if (a == 1) {
            return "Minggu";
        } else if (a == 2) {
            return "Senin";
        } else if (a == 3) {
            return "Selasa";
        } else if (a == 4) {
            return "Rabu";
        } else if (a == 5) {
            return "Kamis";
        } else if (a == 6) {
            return "Jumat";
        } else {
            return "Sabtu";
        }
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

}
