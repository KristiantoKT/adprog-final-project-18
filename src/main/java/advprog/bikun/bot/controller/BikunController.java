package advprog.bikun.bot;

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

public class BikunController {
    public static TemplateMessage requestLocation() {
        List<Action> actions = new ArrayList<Action>();
        actions.add(new URIAction("Share Location", "https://line.me/R/nv/location"));
        return new TemplateMessage("Confirm Location", new ButtonsTemplate("https://cdn.pixabay.com/photo/2017/10/12/18/34/gps-2845363_960_720.png",
                "Cari Halte Bikun Terdekat",
                "Please share your current location",
                actions));
    }
}
