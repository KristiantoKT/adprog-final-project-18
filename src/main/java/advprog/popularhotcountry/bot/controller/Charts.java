package advprog.popularhotcountry.bot.controller;

public class Charts {
    private String song;
    private String artist;

    public Charts(String song, String artist) {
        this.song = song;
        this.artist = artist;
    }

    public String getSong() {

        return song;
    }

    public void setSong(String song) {

        this.song = song;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return artist + " - " + song;
    }
}
