package advprog.anison.bot;

public class Song {

    private String songName;
    private int songId;
    private String songAudioClipUrl;
    private String songImgUrl;

    public Song(String songName, int songId, String songAudioClipUrl) throws Exception {
        this.songName = songName;
        this.songId = songId;
        this.songAudioClipUrl = songAudioClipUrl;

        this.songImgUrl = SongSearch.findImageUrl(this.songName);
    }

    public String getSongName() {
        return songName;
    }

    public int getSongId() {
        return songId;
    }

    public String getSongImgUrl() {
        return songImgUrl;
    }

    public String getSongAudioClipUrl() {
        return songAudioClipUrl;
    }
}
