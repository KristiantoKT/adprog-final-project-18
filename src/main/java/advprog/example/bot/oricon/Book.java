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
        this.name = name;
        this.author = author;
        this.estimatedSales = estimatedSales;
        try {
            this.date = dateFormat.parse(date);
        } catch (ParseException e) {
            this.date = null;
        }
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public Date getDate() {
        return date;
    }

    public int getEstimatedSales() {
        return estimatedSales;
    }

    @Override
    public String toString() {
        return name + " - " + author + " - " + dateFormat.format(date) + " - " + estimatedSales;
    }
}
