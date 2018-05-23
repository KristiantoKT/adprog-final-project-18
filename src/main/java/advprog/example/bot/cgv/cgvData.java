package advprog.example.bot.cgv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class cgvData {
    private final State cinema;

    private HashMap<String, ArrayList<String>> goldSched = new HashMap<>();
    private HashMap<String, ArrayList<String>> sched2D = new HashMap<>();
    private HashMap<String, ArrayList<String>> schde4Dx = new HashMap<>();
    private HashMap<String, ArrayList<String>> velvetSched = new HashMap<>();
    private HashMap<String, ArrayList<String>> sweetboxsched = new HashMap<>();

    private String url;

    public cgvData(State cinema, String url) {
        this.url = url;
        this.cinema = cinema;
    }

    public String getCinemaName() {
        Document document = this.getHtml();
        Elements cinemaName = document.select("div.cinema-info-body>h4");
        return cinemaName.text();
    }

    public Document getHtml() {
        Document doc = null;
        String url = this.url;

        try {
            doc = Jsoup.connect(url)
                    .header("User-Agent", "Chrome").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

    public Elements getScheduleList() {
        Document document = this.getHtml();
        return document.select("ul.showtime-lists");
    }

    public Elements getScheduleType() {
        Document document = this.getHtml();
        return document.select("li.schedule-type");
    }

    public void groupingMovieByClass() {
        ArrayList<String> schedules;

        Elements elements = this.getScheduleType();

        for (Element element : elements) {
            try {
                Elements getMovieClass = element.select("span");
                String movieClass = getMovieClass.text();

                Element schedule = element.nextElementSibling();
                String movieTitle = schedule.select("a").attr("attr-mov");
                String movieSchedule = schedule.text();

                if (movieClass.contains("Sweetbox")) {
                    if (!sweetboxsched.containsKey(movieTitle)) {
                        schedules = new ArrayList<>();
                        schedules.add(movieSchedule);
                        sweetboxsched.put(movieTitle, schedules);
                    }
                } else if (movieClass.contains("Gold Class")) {
                    if (!goldSched.containsKey(movieTitle)) {
                        schedules = new ArrayList<>();
                        schedules.add(movieSchedule);
                        goldSched.put(movieTitle, schedules);
                    }
                } else if (movieClass.contains("Velvet")) {
                    if (!velvetSched.containsKey(movieTitle)) {
                        schedules = new ArrayList<String>();
                        schedules.add(movieSchedule);
                        velvetSched.put(movieTitle, schedules);
                    }
                }

            } catch (Exception e) {
                break;
            }
        }


    }

    public void groupingMovieByDimension() {
        ArrayList<String> schedules;

        Elements elements = this.getScheduleList();

        for (Element element : elements) {
            try {
                Elements getMovieAttr = element.select("li");

                String movieTitle = getMovieAttr.select("a").attr("attr-mov");
                String movieDimension = getMovieAttr.select("a").attr("attr-fmt");
                String movieSchedule = getMovieAttr.text();

                if (movieDimension.equals("2D")) {
                    if (!sched2D.containsKey(movieTitle)) {
                        schedules = new ArrayList<String>();
                        schedules.add(movieSchedule);
                        sched2D.put(movieTitle, schedules);
                    }
                } else if (movieDimension.equals("4DX2D")) {
                    if (!schde4Dx.containsKey(movieTitle)) {
                        schedules = new ArrayList<String>();
                        schedules.add(movieSchedule);
                        schde4Dx.put(movieTitle, schedules);
                    }
                }

            } catch (Exception e) {
                break;
            }
        }
    }

    String cgvGoldClass() {
        return printSchedule(goldSched);
    }


    String cgvRegular2d() {
        return printSchedule(sched2D);
    }


    String cgv4DxCinema() {
        return printSchedule(schde4Dx);
    }


    String cgvVelvet() {
        return printSchedule(velvetSched);
    }


    String cgvSweetBox() {
        return printSchedule(sweetboxsched);
    }

    private String printSchedule(HashMap<String, ArrayList<String>> schedule) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, ArrayList<String>> entry : schedule.entrySet()) {
            String key = entry.getKey();
            ArrayList<String> value = entry.getValue();
            sb.append("( '").append(key).append("' : ").append(value).append(" )\n");
        }

        if (sb.length() == 0) {
            sb.append("Sorry your cinema theater is not available");
        }

        return sb.toString();
    }

}