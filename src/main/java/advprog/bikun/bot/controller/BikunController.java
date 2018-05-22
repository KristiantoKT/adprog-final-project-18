package advprog.bikun.bot.controller;

import advprog.bikun.bot.HalteBikun;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;

import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class BikunController {

    public static int getWaitingTime(HalteBikun halteBikun) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        long min = -1;
        try {
            Date currentTime = dateFormat.parse(dateFormat.format(new Date()));
            for (String time : halteBikun.getJadwal()) {
                Date endTime = dateFormat.parse(time);
                long difference = endTime.getTime() - currentTime.getTime();
                if (difference >= 0) {
                    min = difference;
                    break;
                }
            }
        } catch (ParseException e) {
            return 0;
        }
        return (int) (min / 1000 / 60);
    }

    public static String pesanWaktu(int remainingMinutes, HalteBikun halteBikun) {
        String pesanWaktu = "";
        if (remainingMinutes == -1) {
            pesanWaktu = "Silahkan pesan gojek, karena bikun sudah tidak beroperasi, "
                    + "atau tunggu sampai esok hari pukul " + halteBikun.getJadwal()[0];
        } else {
            pesanWaktu = "Bikun akan datang dalam waktu " + remainingMinutes + " menit lagi";
        }
        return pesanWaktu;
    }


    public static List<Message> requestLocation() {
        List<Action> actions = new ArrayList<Action>();
        actions.add(new URIAction("Share Location", "https://line.me/R/nv/location"));
        TemplateMessage templateMessage = new TemplateMessage("Confirm Location", new ButtonsTemplate("https://cdn.pixabay.com/photo/2017/10/12/18/34/gps-2845363_960_720.png",
                "Cari Halte Bikun Terdekat",
                "Please share your current location",
                actions));
        List<Message> messageList = new ArrayList<>();
        messageList.add(templateMessage);
        return messageList;
    }

    public static List<Message> searchHalte(MessageEvent<LocationMessageContent> event,
                                            HalteBikun[] halteBikuns) {
        LocationMessageContent content = event.getMessage();
        List<Message> result = new ArrayList<>();
        double latitudeUser = content.getLatitude();
        double longitudeUser = content.getLongitude();
        double jarakTerdekat = Double.MAX_VALUE;
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
        int remainingMinutes = BikunController.getWaitingTime(bikunTerdekat);

        String pesan = "";

        if (remainingMinutes == -1) {
            pesan = String.format("Kami merekomendasikan Anda untuk ke %s\n\n"
                            + "%s berjarak %s meter dari posisi Anda\n"
                            + "Namun karena bikun sudah tidak lewat hari ini,"
                            + " kami sarankan untuk memesan ojek online atau"
                            + " kalau mau menunggu sampai besok pukul %s",
                    bikunTerdekat.getNama(), bikunTerdekat.getNama(),
                    jarakTerdekat, bikunTerdekat.getJadwal()[0]);
        } else if (remainingMinutes >= 0) {
            pesan = String.format("Kami merekomendasikan Anda untuk ke %s\n\n"
                            + "%s berjarak %s meter dari posisi Anda\n"
                            + "Bikun akan datang dalam waktu %s menit",
                    bikunTerdekat.getNama(), bikunTerdekat.getNama(), jarakTerdekat,
                    BikunController.getWaitingTime(bikunTerdekat));
        }

        LocationMessage halteBikunLocation = new LocationMessage(
                bikunTerdekat.getNama(), "UI",
                bikunTerdekat.getLatitude(), bikunTerdekat.getLongitude()
        );
        TextMessage halteBikunDetail = new TextMessage(pesan);
        result.add(halteBikunLocation);
        result.add(halteBikunDetail);
        return result;
    }


    public static int distance(double lat1, double lat2, double lon1, double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);
        int hasil = (int) (Math.sqrt(distance) + 0.5);
        return hasil;
    }
}
