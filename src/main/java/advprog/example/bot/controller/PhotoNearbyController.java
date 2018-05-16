package advprog.example.bot.controller;

import advprog.photonearby.PhotoNearby;
import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.ButtonsTemplate;
import com.linecorp.bot.model.message.template.ImageCarouselColumn;
import com.linecorp.bot.model.message.template.ImageCarouselTemplate;

import java.util.ArrayList;
import java.util.List;

public class PhotoNearbyController {

    public static TemplateMessage requestLocation() {
        List<Action> actions = new ArrayList<Action>();
        actions.add(new URIAction("Share Location", "https://line.me/R/nv/location"));
        return new TemplateMessage("Confirm Location", new ButtonsTemplate("https://cdn.pixabay.com/photo/2017/10/12/18/34/gps-2845363_960_720.png",
                "Search Nearby Photo",
                "Please share your current location",
                actions));
    }

    public static List<Message>  searchPhoto(MessageEvent<LocationMessageContent> event) {
        LocationMessageContent content = event.getMessage();
        PhotoNearby photoNearby = PhotoNearby.getInstance();
        String[] searchResult = photoNearby.searchImg(Double.toString(content.getLatitude()),
                Double.toString(content.getLongitude()));
        List<Message> result = new ArrayList<>();
        if (searchResult[0].equals("No image was taken near your location")) {
            result.add(new TextMessage(searchResult[0]));
        } else {
            int counter = 0;
            while (counter < 5 && counter < searchResult.length) {
                result.add(new ImageMessage(searchResult[counter], searchResult[counter]));
                counter++;
            }
        }
        return result;
    }
}