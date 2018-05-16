package advprog.example.bot.livechart.anime;

public class Anime {
    private String title;
    private String synopsis;

    public Anime(String title, String synopsis) {
        this.title = title;
        this.synopsis = synopsis;
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
}

