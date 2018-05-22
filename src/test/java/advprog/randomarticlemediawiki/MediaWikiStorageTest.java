package advprog.randomarticlemediawiki;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class MediaWikiStorageTest {
    MediaWikiStorage store = new MediaWikiStorage();

    @Test
    public void getUrlTest() {
        store.addUrl("hallo");
        String[] hasil = store.getUrl();
        assertTrue(hasil[hasil.length - 1].equals("hallo"));
    }

    @Test
    public void getRandomArticleTest() {
        Article hasil = store.getRandomArticle("http://marvel.wikia.com/api.php");
        assertTrue(!hasil.getTitle().isEmpty());
        assertTrue(!hasil.getLink().isEmpty());
    }
}
