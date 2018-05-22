package advprog.current_weather;

public class CurrentWeatherInfo {
    private String city;
    private String country;
    private String weather;
    private String weatherIcon;
    private String windUnit;
    private String temperatureUnit;

    private Double windSpeed;
    private Long temperature;
    private Long humidity;

    public CurrentWeatherInfo(String city, String country, String weather,
                              String weatherIcon, String windUnit, String temperatureUnit,
                              Double windSpeed, Long temperature, Long humidity) {
        this.city = city;
        this.country = country;
        this.weather = weather;
        this.weatherIcon = weatherIcon;
        this.windUnit = windUnit;
        this.temperatureUnit = temperatureUnit;
        this.windSpeed = windSpeed;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        String info = "Weather at your position (" + city + ", " + country + ")\n"
                + weather + ", " + weatherIcon + "\n"
                + windSpeed + " " + windUnit + "\n"
                + temperature + " " + temperatureUnit + "\n"
                + humidity + " %";
        return info;
    }
}
