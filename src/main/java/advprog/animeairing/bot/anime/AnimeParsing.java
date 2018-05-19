package advprog.animeairing.bot.anime;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;



public class AnimeParsing {
    private String url;
    private String anime;


    public AnimeParsing(String anime) {
        this.url = "http://advpro:Advprobeb@myanimelist.net/api/anime/search.xml?q=";
        this.anime = anime;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void searchAnime(String anime) {
        String newUrl = url + anime;
        newUrl.replace(" ", "-");
        try {
            URL urlObject = new URL(newUrl);
        } catch (IOException e) {
            System.out.println("Error");;
        }
    }



}
