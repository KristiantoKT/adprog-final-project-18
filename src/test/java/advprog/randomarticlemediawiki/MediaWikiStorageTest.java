package advprog.randomarticlemediawiki;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class MediaWikiStorageTest {
    @Test
    public void testGetUrl() {
        MediaWikiStorage.addUrl("testGetUrl");
        String[] hasil = MediaWikiStorage.getUrl();
        assertTrue(hasil[hasil.length - 1].equals("testGetUrl"));
        MediaWikiStorage.hapusFile();
    }

    @Test
    public void testAddUrlSuccess() {
        assertTrue(MediaWikiStorage.addUrl("addUrlSuccess"));
        MediaWikiStorage.hapusFile();
    }

    @Test
    public void testAddUrlFail() {
        MediaWikiStorage.addUrl("addUrlFail");
        assertFalse(MediaWikiStorage.addUrl("addUrlFail"));
        MediaWikiStorage.hapusFile();
    }

    @Test
    public void testIsExistCheck() {
        MediaWikiStorage.addUrl("isExist");
        String[] hasil = MediaWikiStorage.getUrl();
        assertTrue(MediaWikiStorage.isExistCheck(hasil, "isExist"));
        MediaWikiStorage.hapusFile();
    }

    @Test
    public void isWikiMediaApiActive() {
        String url = "https://marvel.wikia.com/api.php";
        assertTrue(MediaWikiStorage.isMediaWikiApiActive(url));
    }

    @Test
    public void testGetRandomArticle() {
        Article hasil = MediaWikiStorage.getRandomArticle("http://marvel.wikia.com/api.php");
        assertTrue(!hasil.getTitle().isEmpty());
        assertTrue(!hasil.getLink().isEmpty());
    }
}
