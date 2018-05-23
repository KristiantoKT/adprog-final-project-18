package advprog.anison.bot;

import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    static Connection con;

    public static Connection datasource() throws Exception {
        String url =
                "postgres://rywaofvvnrgtlj:973e928047edc6bed73292912257db611b0a33185ed3e4bd382608667fc15ced@ec2" +
                        "-50-19-224-165.compute-1.amazonaws.com:5432/d87663ma5r5me1";
        URI dbUri = new URI(url);
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri
                .getPath() + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
        return DriverManager.getConnection(dbUrl,username,password);
    }

    public static void createConnection() throws Exception{
        try {
            con = datasource();
            System.out.println("Connected");
        } catch (Exception e) {
            System.out.println("Connection error");
            e.printStackTrace();
        }

        Statement statement = con.createStatement();
        statement.execute("SET SEARCH_PATH TO public");
    }

    public static void closeConnection() throws SQLException {
        con.close();
    }

    public static int addSong(String id,String title) throws Exception{
        createConnection();
        Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = statement.executeQuery("SELECT * FROM Users WHERE userid = '"+ id + "'" +
        "AND songname = '" + title + "'");
        rs.last();
        int count = rs.getRow();
        rs.beforeFirst();
        if (count > 0) {
            return -1;
        } else {
            statement.execute(
                    String.format(
                            "INSERT INTO users VALUES ('%s','%s')",id,title
                    )
            );
        }
        return 1;
    }

    public static List<String> getSong(String id) throws Exception {
        createConnection();
        Statement s = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = s.executeQuery("SELECT * FROM Users WHERE userid = '" + id + "'"
        );

        ArrayList<String> songs = new ArrayList<>();
        while(rs.next()) {
            songs.add(rs.getString(2));
        }
        return songs;
    }

    public static void deleteSong(String id, String title) throws Exception {
        createConnection();
        Statement s = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        s.execute("DELETE FROM Users WHERE userid = '" + id + "'" +
                "AND songname = '" + title + "'");

    }



}
