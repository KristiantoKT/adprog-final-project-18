package advprog.example.bot.controller;

import advprog.handwrittenintotext.HandwrittingRecognition;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

