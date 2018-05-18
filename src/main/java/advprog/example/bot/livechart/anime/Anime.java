package advprog.example.bot.livechart.anime;

public class Anime {
    private String title;
    private String synopsis;
    private  String genre;

    public Anime(String title, String synopsis, String genre) {
        this.title = title;
        this.synopsis = synopsis;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

