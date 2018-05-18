package advprog.itunes.bot;

public class Song {
    private String title;
    private String artist;
    private String previewUrl;

    public Song(String title, String artist, String previewUrl) {
        this.title = title;
        this.artist = artist;
        this.previewUrl = previewUrl;
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

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
}
