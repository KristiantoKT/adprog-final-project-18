package advprog.example.bot.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class ImageFromLineTest {
    ImageFromLine imageFromLine = new ImageFromLine();

    @Test
    public void test() {
        imageFromLine.setOriginalContentUrl("aaa");
        assertTrue(imageFromLine.getOriginalContentUrl().equalsIgnoreCase("aaa"));
        imageFromLine.setPreviewImageUrl("aaa");
        assertTrue(imageFromLine.getPreviewImageUrl() == "aaa");
        imageFromLine.setType("aaa");
        assertTrue(imageFromLine.getType() == "aaa");
    }
}

