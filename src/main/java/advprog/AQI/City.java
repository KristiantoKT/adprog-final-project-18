package advprog.AQI;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Arrays;
@JsonIgnoreProperties(ignoreUnknown = true)
public class City {
    public String name;
    public String[] geo;

    public City() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getGeo() {
        return geo;
    }

    public void setGeo(String[] geo) {
        this.geo = geo;
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", geo=" + Arrays.toString(geo) +
                '}';
    }
}
