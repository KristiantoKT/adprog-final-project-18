package advprog.top10.bot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import static java.lang.String.format;

public class Billboard200App {
    private String billboard200;
    private List<Song> top10;


    public Billboard200App(String rss) {
        this.billboard200 = billboard200;
        top10 = new ArrayList<>();
        getTop10(billboard200);
    }

    private void getTop10(String rss){
        try {
            Document doc = Jsoup.connect(rss).get();
            Elements chart = doc.getElementsByClass("chart-row");
            for (int i = 0; i < 10; i++) {
                Element e = chart.get(i);
                String title = e.getElementsByClass("chart-row__song").html();
                String artist = e.getElementsByClass("chart-row__artist").html();

                String unescapedTitle = Parser.unescapeEntities(title, false);
                String unescapedArtist = Parser.unescapeEntities(artist, false);

                Song song = new Song(unescapedTitle, unescapedArtist);
                top10.add(song);
            }
        } catch (IOException e) {
            System.out.println("Error!");
        }
    }

    public List<Song> getTop10List() {
        return top10;
    }


    public String printTop10(){
        StringBuilder output = new StringBuilder();
        int chartCounter = 1;
        for (Song song : top10) {
            output.append(format("(%d) %s\n", chartCounter, song.toString()));
            chartCounter++;
        }
        return output.toString();
    }
}
