package advprog.anison.bot;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarouselManagerTest {

    @Test
    public void testEmptyCarouselMaker() {
        assertEquals("TemplateMessage(altText=Carousel alt text, template=CarouselTemplate(columns=[], imageAspectRatio=null, imageSize=null))",
                CarouselManager.carouselMaker(new ArrayList<>()).toString());
    }
}
