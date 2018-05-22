package advprog.anison.bot;

import java.io.FileWriter;
import java.util.ArrayList;

public class SongCsvWriter {

    //Delimiters
    private static final String COMMA = ",";
    private static final String NEWLINE = "\n";

    //CSV file header
    private static final String HEADER = "songName,songId,songAudioClipUrl,songImageUrl";

    public static void writeSongArray(String fileName, ArrayList<Song> songs) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName,false);

            for (Song song : songs) {
                fileWriter.append(song.getSongName());
                fileWriter.append(COMMA);
                fileWriter.append(String.valueOf(song.getSongId()));
                fileWriter.append(COMMA);
                fileWriter.append(song.getSongAudioClipUrl());
                fileWriter.append(COMMA);
                fileWriter.append(song.getSongImgUrl());
                fileWriter.append(NEWLINE);
            }
        } catch (Exception e) {
            System.out.println("error in filewriter");
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                System.out.println("Flush or close error");
            }
        }
    }

    public static void writeSong(String fileName, String songName) {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName,true);

            int songId = SongSearch.findItunesId(songName);
            String songAudioClipUrl = ItuneSearch.getSongClipLink(songId);

            Song song = new Song(songName,songId,songAudioClipUrl);

            //write the attributes to csv
            fileWriter.append(song.getSongName());
            fileWriter.append(COMMA);
            fileWriter.append(String.valueOf(song.getSongId()));
            fileWriter.append(COMMA);
            fileWriter.append(song.getSongAudioClipUrl());
            fileWriter.append(COMMA);
            fileWriter.append(song.getSongImgUrl());
            fileWriter.append(NEWLINE);

            System.out.println("File written successfully");
        } catch (Exception e) {
            System.out.println("error in filewriter");
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (Exception e) {
                System.out.println("Flush or close error");
            }
        }
    }
}
