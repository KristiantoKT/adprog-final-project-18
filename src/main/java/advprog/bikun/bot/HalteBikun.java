package advprog.bikun.bot;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HalteBikun {
    private String nama;
    private double latitude;
    private double longitude;
    private List<String> jadwal;

//    public HalteBikun(String nama, double latitude, double longitude, List<String> jadwal) {
//        this.nama = nama;
//        this.latitude = latitude;
//        this.longitude = longitude;
//        this.jadwal = jadwal;
//    }

    public String getNama() {
        return nama;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<String> getJadwal() {
        return jadwal;
    }

}
