package advprog.favoritehotcountry.bot.controller;

public class Charts {
    private String song;
    private String artist;
    private int position;

    public Charts(String song, String artist, int position) {
        this.song = song;
        this.artist = artist;
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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


}
