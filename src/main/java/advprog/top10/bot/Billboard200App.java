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
    private String bill200Url;
    private List<Song> top10;


    public Billboard200App(String bill200Url) {
        this.bill200Url = bill200Url;
        top10 = new ArrayList<>();
        setTop10(bill200Url);
    }

    private void setTop10(String url){
        try {
            Document docs = Jsoup.connect(url).get();
            Elements links = docs.getElementsByClass("chart-row");
            for (int i = 0; i < 10; i++) {
                Element elem = links.get(i);
                String name = elem.getElementsByClass("chart-row__song").html();
                String artist = elem.getElementsByClass("chart-row__artist").html();

                String formattedNames = Parser.unescapeEntities(name, false);
                String formattedArtists = Parser.unescapeEntities(artist, false);

                Song song = new Song(formattedNames, formattedArtists);
                top10.add(song);
            }
        } catch (IOException e) {
            System.out.println("Error!");
        }
    }

    public List<Song> getTop10List() {
        return top10;
    }

    public String getBillboardUrl() {
        return bill200Url;
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
