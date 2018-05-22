package advprog.bikun.bot;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import org.jetbrains.annotations.NotNull;


public class HalteBikun {
    private String nama;
    private double latitude;
    private double longitude;
    private String imgUrl;
    private String[] jadwal;

    public String getNama() {
        return nama;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String[] getJadwal() {
        return jadwal;
    }

}
