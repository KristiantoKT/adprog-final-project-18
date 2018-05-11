package advprog.example.bot.tropical;

import java.util.ArrayList;
import java.util.List;

public class BillboardTropical {
    private String billboardUrl;
    private List<Song> topTenTropicalList;

    public BillboardTropical(String billboardUrl) {
        this.billboardUrl = billboardUrl;
        topTenTropicalList = new ArrayList<>();
        setTopTenTropicalSongs(billboardUrl);
    }

    private void setTopTenTropicalSongs(String url) {

    }

    public String printTopTenList() {
        return null;
    }

    public String getBillboardUrl() {
        return null;
    }

    public List<Song> getTopTenTropicalList() {
        return null;
    }
}

