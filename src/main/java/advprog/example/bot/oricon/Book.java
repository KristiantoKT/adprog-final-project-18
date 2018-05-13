package advprog.example.bot.oricon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Book {
    private String name;
    private String author;
    private Date date;
    private int estimatedSales;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

    public Book(String name, String author, String date, int estimatedSales) {

    }

    public String getName() {
        return null;
    }

    public String getAuthor() {
        return null;
    }

    public Date getDate() {
        return null;
    }

    public int getEstimatedSales() {
        return -1;
    }

    @Override
    public String toString() {
        return null;
    }
}
