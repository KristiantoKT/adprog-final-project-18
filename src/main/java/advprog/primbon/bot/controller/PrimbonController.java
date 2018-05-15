package advprog.primbon.bot.controller;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        String[] perintah = contentText.split(" ");
        String replyText;
        if (perintah[0].equals("/primbon")) {
            try {
                replyText = tanggalJawa(perintah[1]);
                return new TextMessage(replyText);
            } catch (ParseException e) {
                return new TextMessage("input tanggal salah");
            }
        } else {
            return new TextMessage("Salah keyword");
        }
    }

    public String tanggalJawa(String tanggal) throws ParseException {
        String dataK = "1901-01-01"; //inisiasi tanggal awal
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggalInput = sdf.parse(tanggal);
        Date tanggalAwal = sdf.parse(dataK);
        long differentTimes = tanggalInput.getTime() - tanggalAwal.getTime();
        long diffDay = Math.round(differentTimes / ((double) 24 * 60 * 60 * 1000));
        return hariHelper(diffDay) + " " + wetonHelper(diffDay);
    }

    public String wetonHelper(long differentDays) {
        int hari = (int) (differentDays % 5);
        switch (hari) {
            case 0:
                return "Pahing";
            case 1:
            case -4:
                return "Pon";
            case 2:
            case -3:
                return "Wage";
            case 3:
            case -2:
                return "Kliwon";
            case 4:
            case -1:
                return "Legi";
            default:
                return null;
        }
    }

    public String hariHelper(long differentDays) {
        int hari = (int) differentDays % 7;
        switch (hari) {
            case 0:
                return "Selasa";
            case 1:
            case -6:
                return "Rabu";
            case 2:
            case -5:
                return "Kamis";
            case 3:
            case -4:
                return "Jumat";
            case 4:
            case -3:
                return "Sabtu";
            case 5:
            case -2:
                return "Minggu";
            case 6:
            case -1:
                return "Senin";
            default:
                return null;
        }
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

}
