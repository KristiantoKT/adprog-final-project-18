package advprog.example.bot.controller;

public class Soundtrack {
    private String albumName;
    private String price;

    public Soundtrack(String albumName, String price) {
        this.albumName = albumName;
        this.price = price;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return albumName + ":" + price;
    }
}
