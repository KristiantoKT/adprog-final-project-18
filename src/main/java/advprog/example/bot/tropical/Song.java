package advprog.example.bot.tropical;

/**
 * The class Song implements a blueprint about Song object
 * that contains Song's name and artist who performs it.
 *
 * @author Kristianto
 */
public class Song {
    private String name;
    private String artist;

    public Song(String name, String artist) {
        this.name = name;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {

    }
}
