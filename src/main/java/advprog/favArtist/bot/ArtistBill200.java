package advprog.favArtist.bot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class ArtistBill200 {
    private String billboardUrl;
    private List<Song> songByArtist;

    public ArtistBill200(String billboardUrl){
        this.billboardUrl = billboardUrl;
        songByArtist = new ArrayList<>();
    }

    public String findArtistInChart(String artistToFind) {
        try {
            Document doc = Jsoup.connect(billboardUrl).get();
            Elements links = doc.getElementsByClass("chart-row");

            for (int i = 0; i < 200; i++) {
                Element e = links.get(i);
                String title = e.getElementsByClass("chart-row__song").html();
                String artist = e.getElementsByClass("chart-row__artist").html();

                String unescapedTitle = Parser.unescapeEntities(title, false);
                String unescapedArtist = Parser.unescapeEntities(artist, false);

                if (unescapedArtist.equalsIgnoreCase(artistToFind)) {
                    songByArtist.add(new Song(unescapedTitle, unescapedArtist, i + 1));
                }
            }

            return printSong();

        } catch (IOException e) {
            return "Error!";
        }
    }

    public String printSong() {
        if (songByArtist.size() == 0) {
            return "Error! The artist isn't on the Billboard 200 List!";
        }

        StringBuilder stringBuilder = new StringBuilder();

        int counter = 1;
        for (Song song : songByArtist) {
            stringBuilder.append(song.getArtist()
                    + "\n" + song.getTitle() + "\n" + song.getPosition());
            if (counter < songByArtist.size()) {
                counter++;
                stringBuilder.append("\n");
            }
        }

        songByArtist.clear();

        return stringBuilder.toString();
    }
}
