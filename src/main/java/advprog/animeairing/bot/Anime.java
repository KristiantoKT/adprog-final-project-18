package advprog.animeairing.bot;

public class Anime {
    private String title;
    private String episode;


    public Anime(String title, String episode) {
        this.title = title;
        this.episode = episode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    @Override
    public String toString() {
        return episode + " " + title;
    }
}
