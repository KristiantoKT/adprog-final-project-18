package advprog.bikun.bot.controller;

import advprog.bikun.bot.HalteBikun;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;

import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.ImageCarouselColumn;
import com.linecorp.bot.model.message.template.ImageCarouselTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BikunController {
    public static TemplateMessage requestLocation() {
        List<Action> actions = new ArrayList<Action>();
        actions.add(new URIAction("Share Location", "https://line.me/R/nv/location"));
        return new TemplateMessage("Confirm Location", new ButtonsTemplate("https://cdn.pixabay.com/photo/2017/10/12/18/34/gps-2845363_960_720.png",
                "Cari Halte Bikun Terdekat",
                "Please share your current location",
                actions));
    }

    public static List<Message> searchHalte(MessageEvent<LocationMessageContent> event,
                                            HalteBikun[] halteBikuns) {
        LocationMessageContent content = event.getMessage();
        List<Message> result = new ArrayList<>();
        double latitudeUser = content.getLatitude();
        double longitudeUser = content.getLongitude();
        double jarakTerdekat = 1000000000;
        HalteBikun bikunTerdekat = null;
        for (HalteBikun halteBikun : halteBikuns) {
            double latitudeHalte = halteBikun.getLatitude();
            double longitudeHalte = halteBikun.getLongitude();
            double tmp = distance(latitudeUser, latitudeHalte, longitudeUser, longitudeHalte);
            if (tmp < jarakTerdekat) {
                jarakTerdekat = tmp;
                bikunTerdekat = halteBikun;
            }
        }
        LocationMessage halteBikunLocation = new LocationMessage(
                bikunTerdekat.getNama(), "UI",
                bikunTerdekat.getLatitude(), bikunTerdekat.getLongitude()
        );
        TextMessage halteBikunDetail = new TextMessage(
                String.format("Kami merekomendasikan Anda untuk ke %s\n\n"
                                + "%s berjarak %s meter dari posisi Anda",
                        bikunTerdekat.getNama(), bikunTerdekat.getNama(), jarakTerdekat)
        );
        result.add(halteBikunLocation);
        result.add(halteBikunDetail);
        return result;
    }


    public static double distance(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    public static String differenceMinutes() throws ParseException {
        String time1 = "12:00:00";
        String time2 = "12:01:00";

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = format.parse(time1);
        Date date2 = format.parse(time2);
        long difference = date2.getTime() - date1.getTime();
        return "";
    }
}
