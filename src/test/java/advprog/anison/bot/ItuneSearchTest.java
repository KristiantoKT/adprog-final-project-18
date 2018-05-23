package advprog.anison.bot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class ItuneSearchTest {

    @Test
    public void getSongClipLinkTest() throws Exception{
        int id = 587762424;

        assertEquals("https://audio-ssl.itunes.apple.com/" +
                "apple-assets-us-std-000001/Music/v4/1a/72/e8/" +
                "1a72e8e4-65c5-7e3b-0509-d449895c34b3/mzaf_539876489684497226.plus.aac.p.m4a",ItuneSearch.getSongClipLink(id));

    }

}
