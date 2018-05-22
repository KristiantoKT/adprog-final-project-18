package advprog.anison.bot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongCsvReader {

    //Delimiters
    private static final String COMMA = ",";

    //Song fixed indexes
    private static final int SONG_NAME = 0;
    private static final int SONG_ID = 1;
    private static final int SONG_AUDIO_CLIP_URL = 2;
    //private static final int SONG_IMG_URL = 3;

    //song list
    private static ArrayList<Song> songs = new ArrayList<>();


    public static ArrayList<Song> readSong(String fileName) {

        BufferedReader fileReader = null;

        try {
            String line = "";

            //create the file reader
            fileReader = new BufferedReader(new FileReader(fileName));

            //Read the lines
            while ((line = fileReader.readLine()) != null) {
                //Get all the song attributes in the line
                String[] attrs = line.split(COMMA);
                if (attrs.length > 0) {
                    //create the new song object
                    Song song = new Song(attrs[SONG_NAME],
                            Integer.parseInt(attrs[SONG_ID]),attrs[SONG_AUDIO_CLIP_URL]);
                    songs.add(song);

                }
            }

            //Print the songs list
            for (Song song : songs) {
                System.out.println(song.getSongName());
            }
        } catch (Exception e) {
            System.out.println("Error in csv file reader!");
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error while closing filereader!");
            }
        }
        return songs;
    }


}
