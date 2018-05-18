package advprog.example.bot.controller;

public class Soundtrack {
    private String albumName;

    public Soundtrack(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }


    @Override
    public String toString() {
        return albumName;
    }
}
