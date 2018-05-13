package advprog.example.bot.oricon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookTest {
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("A Book For You", "Unknown", "2018-12", 12345);
    }

    @Test
    void getName() {
        assertEquals("A Book For You", book.getName());
    }

    @Test
    void getAuthor() {
        assertEquals("Unknown", book.getAuthor());
    }

    @Test
    void getDate() throws Exception {
        Date expected = new SimpleDateFormat("yyyy-MM").parse("2018-12");
        assertEquals(expected, book.getDate());
    }

    @Test
    void getEstimatedSales() {
        assertEquals(12345, book.getEstimatedSales());
    }

    @Test
    void toStringTest() {
        String output = book.toString();
        assertEquals(output, "A Book For You - Unknown - 2018-12 - 12345");
    }
}