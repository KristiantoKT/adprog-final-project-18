package advprog.example.bot.primbon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Primbon {
    private String tanggal;

    public Primbon(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String tanggalJawa(String tanggal) throws ParseException {
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

    public String weton(long a) {
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

    public String days(int a) {
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
}
