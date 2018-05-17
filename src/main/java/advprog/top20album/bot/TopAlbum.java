package advprog.top20album.bot;

import java.util.ArrayList;
import java.util.List;

public class TopAlbum {

    private String vgmdbUrl;
    private List<Album> listOfAlbums;

    public TopAlbum(String vgmdbUrl) {
        this.vgmdbUrl = vgmdbUrl;
        listOfAlbums = new ArrayList<>();
    }

    public String getTop20Albums(String vgmdbUrl) {
        return "";
    }

    public String printAlbum() {
        return "";
    }
}
