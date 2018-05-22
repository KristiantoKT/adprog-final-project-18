package advprog.example.bot.controller;

import advprog.currentweather.CurrentWeatherHandler;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.UserSource;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.util.logging.Logger;

@LineMessageHandler
public class EchoController {

    private static final Logger LOGGER = Logger.getLogger(EchoController.class.getName());
    private static CurrentWeatherHandler currentWeatherHandler =
            CurrentWeatherHandler.getInstance();
    private boolean checkingForCurrentWeather = false;

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();

        String reply = "Invalid command";
        String temp;
        if (contentText.split(" ")[0].equals("/echo")) {
            temp = contentText.replace("/echo", "");
            reply = temp.substring(1);
        } else if (contentText.split(" ")[0].equals("/weather")) {
            checkingForCurrentWeather = true;
            reply = "Waiting for location input";
        } else if (contentText.split(" ")[0].equals("/configure_weather")) {
            String config = contentText.split(" ")[1];
            if (config.equals("metric") || config.equals("imperial")) {
                currentWeatherHandler.setUnitType(config);
                reply = "Weather configuration changed to " + config + " system";
            }
        } else if (contentText.toLowerCase().contains("cuaca di ")
                && event.getSource() instanceof GroupSource) {
            String cityName = currentWeatherHandler.extractCityName(contentText.toLowerCase());
            reply = currentWeatherHandler.getCurrentWeatherInfo(cityName);
        }
        return new TextMessage(reply);
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

    @EventMapping
    public Message handleLocationMessageEvent(MessageEvent<LocationMessageContent> event) {
        Message message = null;
        if (checkingForCurrentWeather && event.getSource() instanceof UserSource) {
            String lat = Double.toString(event.getMessage().getLatitude());
            String lon = Double.toString(event.getMessage().getLongitude());
            String info = currentWeatherHandler.getCurrentWeatherInfo(lat,lon);
            message = new TextMessage(info);
            checkingForCurrentWeather = false;
        }

        return message;
    }

}
