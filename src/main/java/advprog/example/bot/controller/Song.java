package advprog.example.bot.controller;

public class Song {
    private String name;
    private String artist;
    private int position;

    public Song(String name, String artist, int position) {
        this.name = name;
        this.artist = artist;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return artist + " - " + name;
    }
}