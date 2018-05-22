package advprog.randomarticlemediawiki;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MediaWikiStorageTest {
    MediaWikiStorage store = new MediaWikiStorage();

    @Test
    public void getUrlTest() {
        store.addUrl("hallo");
        String[] hasil = store.getUrl();
        assertTrue(hasil[hasil.length-1].equals("hallo"));
    }

    @Test
    public void getRandomArticleTest() {
        Article hasil = store.getRandomArticle("http://marvel.wikia.com/api.php");
        assertTrue(!hasil.getTitle().isEmpty());
        assertTrue(!hasil.getLink().isEmpty());
    }
}
