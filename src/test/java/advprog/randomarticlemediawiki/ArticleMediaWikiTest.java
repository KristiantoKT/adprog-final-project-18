package advprog.randomarticlemediawiki;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ArticleMediaWikiTest {
    ArticleMediaWiki article;

    @Before
    public void setUp(){
        article = new ArticleMediaWiki(null, null, null);
    }

    @Test
    public void testGetRandomArticle(){
        assertEquals(null, article.getRandomArticle());
    }

}
