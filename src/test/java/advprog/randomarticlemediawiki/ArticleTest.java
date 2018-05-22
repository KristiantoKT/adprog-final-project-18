package advprog.randomarticlemediawiki;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ArticleTest {
    Article article;

    @Before
    public void setUp() {
        article = new Article("A", "B", "C");
    }

    public void getTitleTest() {
        assertEquals("A", article.getTitle());
    }

    public void setTitleTest() {
        article.setTitle("Z");
        assertEquals("Z", article.getTitle())
    }

}
