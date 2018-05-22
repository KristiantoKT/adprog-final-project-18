package advprog.fake.news.controller;

import advprog.fake.news.FakeNews;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;



@LineMessageHandler
public class FakeNewsController {
    private static final Logger LOGGER = Logger.getLogger(FakeNewsController.class.getName());
    public Map<String, FakeNews> mapCsv = loadCsv();

    public FakeNewsController() throws IOException {

    }

    @EventMapping
    public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event)
            throws IOException {
        LOGGER.fine(String.format("TextMessageContent(timestamp='%s',content='%s')",
                event.getTimestamp(), event.getMessage()));
        TextMessageContent content = event.getMessage();
        String contentText = content.getText();
        if (event.getSource().getClass().getTypeName().endsWith("UserSource")) {
            return checkPersonalMessage(contentText);
        } else {
            return new TextMessage(checkGroupMessage(contentText));
        }
    }

    private String checkGroupMessage(String contentText) {
        String[] arrayOfUrl = contentText.split("://");
        String urlCheck;
        if (arrayOfUrl.length > 1) {
            urlCheck = arrayOfUrl[1];
        } else {
            urlCheck = arrayOfUrl[0];
        }
        FakeNews fakeNews = mapCsv.get(urlCheck);
        if (fakeNews == null) {
            return "URL doesn't exist";
        } else {
            if (fakeNews.getType2().length() > 2) {
                if (fakeNews.getType3().length() > 2) {
                    return urlCheck + " is " + fakeNews.getType() + ", "
                            + fakeNews.getType2() + ",and " + fakeNews.getType3();
                } else {
                    return urlCheck + " is "
                            + fakeNews.getType() + " and " + fakeNews.getType2();
                }
            } else {
                return urlCheck + " is " + fakeNews.getType();
            }
        }
    }

    private TextMessage checkPersonalMessage(String contentText) throws IOException {
        String command = contentText.split(" ")[0];
        if (command.equals("/is_fake")) {
            return new TextMessage(checkNews(contentText.split(" ")[1], "fake"));
        } else if (command.equals("/is_satire")) {
            return new TextMessage(checkNews(contentText.split(" ")[1], "Satire"));
        } else if (command.equals("/is_conspiracy")) {
            return new TextMessage(checkNews(contentText.split(" ")[1], "Conspiracy"));
        } else if (command.equals("/add_filter")) {
            return new TextMessage(addFilter(contentText.split(" ")[1], contentText.split(" ")[2]));
        } else {
            return new TextMessage("Command not found!");
        }
    }


    private String addFilter(String url, String type) {
        String[] arrayOfUrl = url.split("://");
        String urlCheck;

        if (arrayOfUrl.length > 1) {
            urlCheck = arrayOfUrl[1];
        } else {
            urlCheck = arrayOfUrl[0];
        }

        FakeNews fakeNews = mapCsv.get(urlCheck);
        if (fakeNews == null) {
            return "URL doesn't exist";
        } else {
            if (fakeNews.getType().equalsIgnoreCase(type)) {
                return "filter is already present";
            } else {
                if (fakeNews.getType2().equalsIgnoreCase(type)) {
                    return "filter is already present";
                } else if (fakeNews.getType2().length() < 2) {
                    mapCsv.put(urlCheck, new FakeNews(fakeNews.getType(),
                            type, "", fakeNews.getSourceNotes()));
                    return "added";
                } else {
                    if (fakeNews.getType3().equalsIgnoreCase(type)) {
                        return "filter is already present";
                    } else if (fakeNews.getType3().length() < 2) {
                        mapCsv.put(urlCheck, new FakeNews(fakeNews.getType(),
                                fakeNews.getType2(), type, fakeNews.getSourceNotes()));
                        return "added";
                    } else {
                        return "criteria full";
                    }
                }
            }
        }
    }

    private String checkNews(String url, String type) {
        boolean result = false;
        String[] arrayOfUrl = url.split("://");
        String urlCheck;

        if (arrayOfUrl.length > 1) {
            urlCheck = arrayOfUrl[1];
        } else {
            urlCheck = arrayOfUrl[0];
        }

        FakeNews fakeNews = mapCsv.get(urlCheck);
        if (fakeNews == null) {
            return "URL doesn't exist";
        } else {
            if (type.equalsIgnoreCase("Fake")) {
                if (fakeNews.getType().equalsIgnoreCase("fake")) {
                    result = true;
                } else if (fakeNews.getType2().equalsIgnoreCase("fake")) {
                    result = true;
                } else if (fakeNews.getType3().equalsIgnoreCase("fake")) {
                    result = true;
                } else {
                    result = false;
                }
            } else if (type.equalsIgnoreCase("Satire")) {
                if (fakeNews.getType().equalsIgnoreCase("satire")) {
                    result = true;
                } else if (fakeNews.getType2().equalsIgnoreCase("satire")) {
                    result = true;
                } else if (fakeNews.getType3().equalsIgnoreCase("satire")) {
                    result = true;
                } else {
                    result = false;
                }
            } else if (type.equalsIgnoreCase("Conspiracy")) {
                if (fakeNews.getType().equalsIgnoreCase("conspiracy")) {
                    result = true;
                } else if (fakeNews.getType2().equalsIgnoreCase("conspiracy")) {
                    result = true;
                } else if (fakeNews.getType3().equalsIgnoreCase("conspiracy")) {
                    result = true;
                } else {
                    result = false;
                }
            }
            if (result) {
                return "True";
            } else {
                return "False";
            }
        }
    }

    private Map<String, FakeNews> loadCsv() throws IOException {
        FileReader fileReader =
                new FileReader("sources.csv");
        BufferedReader bufferedReader =
                new BufferedReader(fileReader);
        Map<String, FakeNews> mapCsv = new HashMap<String, FakeNews>();
        String line = bufferedReader.readLine();
        line = bufferedReader.readLine();
        while (line != null) {
            String[] arrayOfCsv = line.split(",");
            if (arrayOfCsv.length > 5) {
                for (int x = 5; x < arrayOfCsv.length; x++) {
                    arrayOfCsv[4] += arrayOfCsv[x];
                }
            }
            if (arrayOfCsv.length == 2) {
                mapCsv.put(arrayOfCsv[0], new FakeNews(arrayOfCsv[1], "", "", ""));
            } else if (arrayOfCsv.length == 3) {
                mapCsv.put(arrayOfCsv[0], new FakeNews(arrayOfCsv[1],
                        arrayOfCsv[2], "", ""));
            } else if (arrayOfCsv.length == 4) {
                mapCsv.put(arrayOfCsv[0], new FakeNews(arrayOfCsv[1],
                        arrayOfCsv[2], arrayOfCsv[3], ""));
            } else {
                mapCsv.put(arrayOfCsv[0], new FakeNews(arrayOfCsv[1],
                        arrayOfCsv[2], arrayOfCsv[3], arrayOfCsv[4]));
            }
            line = bufferedReader.readLine();
        }
        return mapCsv;
    }

    @EventMapping
    public void handleDefaultMessage(Event event) {
        LOGGER.fine(String.format("Event(timestamp='%s',source='%s')",
                event.getTimestamp(), event.getSource()));
    }

}
