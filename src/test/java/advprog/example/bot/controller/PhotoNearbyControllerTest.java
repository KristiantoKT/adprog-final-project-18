package advprog.example.bot.controller;

import advprog.example.bot.EventTestUtil;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PhotoNearbyControllerTest {

    @Test
    void testRequestLocation () {
        TemplateMessage message = PhotoNearbyController.requestLocation();
        assertEquals("Confirm Location", message.getAltText());

        ButtonsTemplate button = (ButtonsTemplate) message.getTemplate();
        assertEquals("Search Nearby Photo", button.getTitle());
    }

    @Test
    void testSearchPhoto () {
        MessageEvent<LocationMessageContent> event =
                EventTestUtil.createDummyLocationMessage("My Location", "Brooklyn",
                                                        40.661292, -73.968931);
        List<Message> reply = PhotoNearbyController.searchPhoto(event);

        assertNotNull(reply);
    }
}
