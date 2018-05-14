package advprog.favartist.bot;

public class Song {
    private String title;
    private String artist;
    private int position;

    public Song(String title, String artist, int position) {
        this.title = title;
        this.artist = artist;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getPosition() {
        return position; 
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return artist + " - " + title;
    }

}
