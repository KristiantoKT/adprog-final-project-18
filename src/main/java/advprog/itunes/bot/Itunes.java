package advprog.itunes.bot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Itunes {

    private String artist;
    private Song pickedSong;
    private List<Song> songByArtist;


    public Itunes() {
    }

    public Itunes(String artist) {
        this.artist = artist;
        songByArtist = new ArrayList<>();
        findSongsByArtist(artist);
    }

    public void findSongsByArtist(String artist) {
        //find song by Artist
    }

    public void songRandomizer(List<Song> songByArtist) {
        Random rand = new Random();
        int size = songByArtist.size();
        int pickedNumber = rand.nextInt(size) + 1;

        pickedSong = songByArtist.get(pickedNumber);
        getPreviewUrl(pickedSong);
    }

    public void getPreviewUrl(Song song) {
        //get the url for song preview
        song.getPreviewUrl();
    }
}
