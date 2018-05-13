package advprog.example.bot.oricon;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class OriconBooksTest {
    private OriconBooks oriconBooks;

    @BeforeEach
    void setUp() {
        String url = "https://www.oricon.co.jp/rank/ob/w/";
        String date = "2018-05-14";
        oriconBooks = new OriconBooks(url, date);
    }

    @Test
    void printTopTenList() {
        String expectedOutput = oriconBooks.printTopTenList();
        assertTrue(expectedOutput.contains("漫画 君たちはどう生きるか"));
    }
}