package advprog.randomarticlemediawiki;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ArticleTest {
    Article article;

    @Before
    public void setUp() {
        article = new Article("A", "B", "C", "D");
    }

    @Test
    public void getTitleTest() {
        assertEquals("A", article.getTitle());
    }

    public void setTitleTest() {
        article.setTitle("Z");
        assertEquals("Z", article.getTitle());
    }

}
