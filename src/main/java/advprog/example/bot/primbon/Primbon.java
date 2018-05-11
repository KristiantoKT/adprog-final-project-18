package advprog.example.bot.primbon;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Primbon {
    private String tanggal;
    public Primbon(String tanggal){
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String tanggalJawa(String tanggal){
        String dataK = "1901-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date hari = sdf.parse(tanggal);
        Date konstan = sdf.parse(dataK);
        long a = hari.getTime()-konstan.getTime();
        long diffDay = a /(24 * 60 * 60 * 1000);
        return days(hari.getDay()) + " " + weton(diffDay);
    }
    public String weton(long a){
        if(a%5==0){
            return "Pon";
        }
        else if(a%5==1){
            return "Wage";
        }
        else if(a%5==2){
            return "Kliwon";
        }
        else if(a%5==3){
            return "Legi";
        }
        else{
            return "Pahing";
        }
    }

    public String days(int a){
        if(a==0){
            return "Minggu";
        }
        else if(a==1){
            return "Senin";
        }
        else if(a==2){
            return "Selasa";
        }
        else if(a==3){
            return "Rabu";
        }
        else if(a==4){
            return "Kamis";
        }
        else if(a==5){
            return "Jumat";
        }
        else{
            return "Sabtu";
        }
    }
}
