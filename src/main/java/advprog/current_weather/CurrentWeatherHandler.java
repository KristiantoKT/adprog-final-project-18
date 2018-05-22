package advprog.current_weather;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CurrentWeatherHandler {
    private final static String apiKey = "69330a92f9cd35a7e2f898df3f157ced";
    private final static String apiLink = "https://api.openweathermap.org/data/2.5/weather?";
    private final static String errorMsg = "Invalid City name / coordinate";
    private static CurrentWeatherHandler instance = new CurrentWeatherHandler();
    private static String unitType = "metric";

    public static CurrentWeatherHandler getInstance() {
        return instance;
    }

    public void setUnitType(String newUnitType) {
        unitType = newUnitType;
    }

    public String getUnitType() {
        return unitType;
    }

    // depok -6.391256, 106.819511
    public String getCurrentWeatherInfo(String lat, String lon) {
        String target = apiLink + "lat=" + lat + "&lon=" + lon + "&units=" + unitType
                + "&appid=" + apiKey;

        CurrentWeatherInfo result = extractWeatherInfo(target);
        if (result != null) {
            return result.toString();
        } else {
            return errorMsg;
        }
    }

    public String getCurrentWeatherInfo(String city) {
        String target = apiLink + "q=" + city + "&units=" + unitType
                + "&appid=" + apiKey;

        CurrentWeatherInfo result = extractWeatherInfo(target);
        if (result != null) {
            return result.toString();
        } else {
            return errorMsg;
        }
    }

    private CurrentWeatherInfo extractWeatherInfo(String target) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(target, String.class);

            JSONObject json = (JSONObject) JSONValue.parse(responseEntity.getBody());

            String city = (String) json.get("name");
            String country = (String) ((JSONObject) json.get("sys")).get("country");
            String weather = (String) ((JSONObject)((JSONArray)json.get("weather")).get(0)).get("main");
            String weatherIcon = (String) ((JSONObject)((JSONArray)json.get("weather")).get(0))
                    .get("description");
            String windUnit = getUnitType().equals("metric") ? "km/h" : "mph";
            String temperatureUnit = getUnitType().equals("metric") ? "c" : "f";
            Double windSpeed = (Double) ((JSONObject)json.get("wind")).get("speed");
            Long temperature = (Long) ((JSONObject)json.get("main")).get("temp");
            Long humidity = (Long) ((JSONObject)json.get("main")).get("humidity");
            CurrentWeatherInfo currentWeatherInfo = new CurrentWeatherInfo(
                    city,country,weather,
                    weatherIcon,windUnit,temperatureUnit,
                    windSpeed,temperature,humidity);

            return currentWeatherInfo;
        } catch (Exception e) {
            return null;
        }

    }

    public String extractCityName(String text) {
        text = text.toLowerCase();
        text = text.substring(text.indexOf("cuaca di "));
        return text.replace("cuaca di ","").split(" ")[0];
    }
}
