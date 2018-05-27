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
    public void testGetTitle() {
        assertEquals("A", article.getTitle());
    }

    @Test
    public void testSetTitle() {
        article.setTitle("Z");
        assertEquals("Z", article.getTitle());
    }

    @Test
    public void testGetSummary() {
        assertEquals("B", article.getSummary());
    }

    @Test
    public void testSetSummary() {
        article.setSummary("Z");
        assertEquals("Z", article.getSummary());
    }

    @Test
    public void testGetUrlImage() {
        assertEquals("D", article.getImageUrl());
    }

    @Test
    public void testSetUrlImage() {
        article.setImageUrl("Z");
        assertEquals("Z", article.getImageUrl());
    }

    @Test
    public void testGetLink() {
        assertEquals("C", article.getLink());
    }

    @Test
    public void testSetLink() {
        article.setLink("Z");
        assertEquals("Z", article.getLink());
    }

}
