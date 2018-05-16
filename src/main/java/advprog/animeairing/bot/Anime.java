package advprog.animeairing.bot;

public class Anime {
    private String title;
    private int episode;


    public Anime (String title, int episode) {
        this.title = title;
        this.episode = episode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    @Override
    public String toString(){
        return title + " " + episode;
    }
}
