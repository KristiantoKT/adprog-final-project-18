package advprog.example.bot.controller;

public class Soundtrack {
    private String albumName;
    private String price;

    public Soundtrack(String albumName, String price) {
        this.albumName = albumName;
        this.price = price;
    }

    @Override
    public String toString() {
        return albumName + " : " + price;
    }
}
